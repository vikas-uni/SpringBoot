package com.howtodoinjava.example.apigateway.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

//good guide to security- https://www.marcobehler.com/guides/spring-security

//docs- https://spring.io/guides/topicals/spring-security-architecture/

/**
 * https://medium.com/@haytambenayed/how-does-spring-security-work-internally-525d359d7af
 * 
 * SecurityFilterChain : Spring Security maintains a filter chain internally
 * where each of the filters is invoked in a specific order. Each filter will
 * try to process the request and retrieve authentication information from it.
 * For example, we have the UsernamePasswordAuthenticationFilter which is used
 * in case of a POST request with username and password parameters (typically
 * with a login page). the ordering of the filters is important as there are
 * dependencies between them. You can read here if you want to know more about
 * filters and ordering. Or, you can directly navigate to the FilterComparator
 * class to see the implementation details.
 * 
 * AuthenticationManager : This is an interface whose implementation
 * (ProviderManager) has a list of configured AuthenticationProviders that are
 * used for authenticating user requests.
 * 
 * AuthenticationProvider : An AuthenticationProvider is an abstraction for
 * fetching user information from a specific repository (like a database, LDAP,
 * custom third party source, etc.). It uses the fetched user information to
 * validate the supplied credentials. (e.g: DaoAuthenticationProvider,
 * LdapAuthenticationProvider, OpenIDAuthenticationProvider …) When talking
 * about AuthenticationProvider, we usually come across the UserDetailsService.
 * There is often a confusion between both, although they have different roles.
 * AuthenticationProvider authenticates(compares) the request credentials
 * against system credentials. UserDetailsService is purely a DAO for user data
 * and performs no other function other than to supply that data that match with
 * user provided Username. It does not tell the application whether
 * authentication is successful or failed.
 * 
 * Authentication Flow Now that we know the fundamental elements of Spring
 * Security’s architecture, let’s describe the execution of the authentication
 * flow :
 * 
 * When an incoming request reaches our system, Spring Security starts by
 * choosing the right security filter to process that request (Is the request a
 * POST containing username and password elements? =>
 * UsernamePasswordAuthenticationFilter is chosen. Is the request having a
 * header “Authorization : Basic base64encoded(username:password)”? =>
 * BasicAuthenticationFilter is chosen… and so the chaining goes on). When a
 * filter had successfully retrieved Authentication informations from the
 * request, the AuthenticationManager is invoked to authenticate the request.
 * via its implementation, the AuthenticationManager goes through each of the
 * provided AuthenticationProvider(s) and try to authenticate the user based on
 * the passed Authentication Object. when the Authentication is successful, and
 * a matching user if found, an Authentication Object containing the user
 * Authorities (which will be used to manage the user access to the system’s
 * resources) is returned and set into the SecurityContext.
 * 
 * @author vikasgond
 *
 */

//this provides simple basic auth security to specified resource endpoint
@Configuration
@EnableWebSecurity(debug = true)//we'll enable security debugging which will log detailed security information on each request.
//We can enable security debugging using the debug property:
//This way, when we send a request to the server, all the request information will be logged.We'll also be able to see the entire security filter chain:
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	// Create 2 users for demo

	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception {
	 * 
	 * auth.inMemoryAuthentication().withUser("user").password("password").roles(
	 * "USER").and().withUser("admin") .password("password").roles("USER", "ADMIN");
	 * 
	 * }
	 */

	// Secure the endpoins with HTTP Basic authentication
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// the context path- /emp-api-gateway in url is added-
		// http://localhost:8010/emp-api-gateway/employeeDetails/111
		// therefore, in path matchers '**/' is appended before 'employeeDetails'
		http
		//this will disable caching of user credentials
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
         
				// HTTP Basic authentication
				.httpBasic().and().authorizeRequests()
				.antMatchers("/", "/testMethod/").permitAll() // no authentication required for this endpoint
				.antMatchers(HttpMethod.GET, "**/employeeDetails/**").hasRole("USER")
				.antMatchers(HttpMethod.POST, "**/employeeDetails").hasRole("ADMIN")
				.antMatchers(HttpMethod.PUT, "**/employeeDetails/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.PATCH, "**/employeeDetails/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE, "**/employeeDetails/**").hasRole("ADMIN")

				.and().csrf().disable().formLogin().disable();
	}

	// this can also be used in place of protected void
	// configure(AuthenticationManagerBuilder auth)
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		UserDetails user = User.withUsername("user").password(passwordEncoder().encode("secret")).roles("USER").build();
		UserDetails userAdmin = User.withUsername("admin").password(passwordEncoder().encode("secret"))
				.roles("ADMIN", "USER").build();
		List<UserDetails> usrs = Arrays.asList(user, userAdmin);
		return new InMemoryUserDetailsManager(usrs);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}