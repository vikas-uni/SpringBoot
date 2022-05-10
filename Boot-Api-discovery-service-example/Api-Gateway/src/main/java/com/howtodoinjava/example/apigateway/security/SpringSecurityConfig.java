package com.howtodoinjava.example.apigateway.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

//this provides simple basic auth security to specified resource endpoint
@Configuration
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
	 * auth.inMemoryAuthentication().withUser("user").password("{noop}password").
	 * roles("USER").and().withUser("admin")
	 * .password("{noop}password").roles("USER", "ADMIN");
	 * 
	 * }
	 */
	 

	// Secure the endpoins with HTTP Basic authentication
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				// HTTP Basic authentication
//				.httpBasic()
//				.and()
				.authorizeRequests().antMatchers("/","/testMethod/").permitAll()  //no authentication required for this endpoint
				.antMatchers(HttpMethod.GET, "/employeeDetails/**").hasRole("USER")
				.antMatchers(HttpMethod.POST, "/employeeDetails").hasRole("ADMIN")
				.antMatchers(HttpMethod.PUT, "/employeeDetails/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.PATCH, "/employeeDetails/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE, "/employeeDetails/**").hasRole("ADMIN")
				.and().csrf().disable()
				.formLogin().disable();
	}

	@Bean
    @Override
    public UserDetailsService userDetailsService() {
    	UserDetails user=User.withUsername("user").password(passwordEncoder().encode("secret")).
    			roles("USER").build();
    	UserDetails userAdmin=User.withUsername("admin").password(passwordEncoder().encode("secret")).
    			roles("ADMIN").build();
    	List<UserDetails> usrs = Arrays.asList(user,userAdmin);
        return new InMemoryUserDetailsManager(usrs);
    }
	
	 @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new  BCryptPasswordEncoder();
	    }

}