package com.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*The @EnableWebSecurity is a marker annotation. What that means is that it allows Spring to find and automatically apply this class to the security of the application.
We need to secure our API’s by restricting which roles are able to execute a particular method. This is achieved by adding the annotation @EnableGlobalMethodSecurity(prePostEnabled = true}.*/

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	/*
	 * Authentication Now Spring Security uses something called an
	 * AuthenticationManager to validate if a given user has the right credentials
	 * (based on username and password). In fact, the AuthenticationManager
	 * Interface has exactly one method authenticate which is called to verify if
	 * the username and password provided by a user are truthy. But the
	 * AuthenticationManager needs to know where the user’s username and password
	 * have been stored. That is why we override the configure method where Spring
	 * will pass an AuthenticationManagerBuilder. The AuthenticationManagerBuilder
	 * accepts a custom implementation of the UserDetailsService interface (which we
	 * will implement when we are building our services). Also at this stage, if we
	 * are using some form of encryption to store our password in the database, the
	 * AuthenticationManager needs to know about that as well. It’s actually a very
	 * bad idea to store a password as plaintext. Here, we will be using BCrypt to
	 * encode our passwords. The AuthenticationManager we just configured is added
	 * to the Spring Application Context and is added as a bean by overriding the
	 * authecationManagerBean method.
	 * 
	 * @Autowired
	 */
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// configure AuthenticationManager so that it knows from where to load
		// user for matching credentials
		// Use BCryptPasswordEncoder
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/*
	 * Authorization To set up Authorization, we again need to provide the
	 * configuration by overriding the configure method, where we are passed a
	 * reference to the default HttpSecurity configuration. Here we are configuring
	 * such that we will require authentication for all requests, with the exception
	 * of /users/register & /users/authenticate (We require those two endpoints to
	 * be available to all users to sign-up or login). For the graceful handling of
	 * Unauthorized requests, we pass along a class that implements
	 * AuthenticationEntryPoint. We will return a 401 Unauthorized when we encounter
	 * an exception. Because we are using JWT to store roles, we need to translate
	 * that into something that Spring Security can understand. The JWT Token needs
	 * to be parsed to fetch roles that the SpringSecurityContext needs to become
	 * aware of before it goes on to check if the API’s permissions will allow it.
	 * Hence we pass along the JwtAuthenticationFilter (Which we will come to in a
	 * later step).
	 */	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// We don't need CSRF for this example
		httpSecurity.csrf().disable()
				// dont authenticate this particular request
				.authorizeRequests().antMatchers("/authenticate", "/register").permitAll().
				// all other requests need to be authenticated
				anyRequest().authenticated().and().
				// make sure we use stateless session; session won't be used to
				// store user's state.
				exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
}