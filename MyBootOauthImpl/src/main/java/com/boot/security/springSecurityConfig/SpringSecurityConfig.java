package com.boot.security.springSecurityConfig;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	// use this to get client details from DB
	@Resource(name = "userService")
	private UserDetailsService userDetailsService;

	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	/*
	 * @Bean
	 * 
	 * @Override public UserDetailsService userDetailsService() {
	 * 
	 * UserDetails
	 * user=User.builder().username("user").password(passwordEncoder().encode(
	 * "secret")). roles("USER").build(); UserDetails
	 * userAdmin=User.builder().username("admin").password(passwordEncoder().encode(
	 * "secret")). roles("ADMIN").build(); return new
	 * InMemoryUserDetailsManager(user,userAdmin); }
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/", "/publicApi", "/register").permitAll()
				.antMatchers("/privateApi").authenticated().antMatchers("/privateApi").hasRole("USER")
				.antMatchers("/admin").hasRole("ADMIN").and().formLogin();
		
		// .disable(); this is commented to enable form login to test authorization_code
		// flow. the default
		// login page
		// is displayed when we hit GET:
		// http://localhost:8111/oauth/authorize?client_id=client&response_type=code&redirect_uri=http://localhost:8111/privateApi&scope=read
		// a page appears asking for user and password
		
		// sample logout customization
		http.logout().deleteCookies("remove").invalidateHttpSession(true);
	}

}
