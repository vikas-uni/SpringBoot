package boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

//old config, working but deprecated
//@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	// Create 2 users for demo
	
	  @Override 
	  protected void configure(AuthenticationManagerBuilder auth) throws
	  Exception {
	  
	  auth.inMemoryAuthentication().withUser("user").password("{noop}password").
	  roles("USER").and().withUser("admin")
	  .password("{noop}password").roles("USER", "ADMIN");
	  
	  }
	 

	// Secure the endpoins with HTTP Basic authentication
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				// HTTP Basic authentication
				.httpBasic().and().authorizeRequests()
				.antMatchers(HttpMethod.GET, "/books/**").hasRole("USER")
				.antMatchers(HttpMethod.POST, "/books").hasRole("ADMIN")
				.antMatchers(HttpMethod.PUT, "/books/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.PATCH, "/books/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE, "/books/**").hasRole("ADMIN")
				.and().csrf().disable()
				.formLogin()
				.disable();
	}

	// this also works in place of protected void configure(AuthenticationManagerBuilder auth)
	/*
	 * @Bean public UserDetailsService userDetailsService() { //ok for demo
	 * User.UserBuilder users = User.withDefaultPasswordEncoder();
	 * 
	 * InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
	 * manager.createUser(users.username("user").password("pass").roles("USER").
	 * build());
	 * manager.createUser(users.username("admin").password("pass").roles("USER",
	 * "ADMIN").build()); return manager; }
	 */
	 

}