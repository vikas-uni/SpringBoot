package demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//old way, now deprecated
//@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	// Create 2 users for demo
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.inMemoryAuthentication().passwordEncoder(passwordEncoder()).withUser("user")
				.password("password").roles("USER").and().withUser("admin")
				.password("password").roles("USER", "ADMIN");

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Secure the endpoins with HTTP Basic authentication
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				// HTTP Basic authentication
				.httpBasic().and().authorizeRequests().antMatchers(HttpMethod.GET, "/javainuse-kafka/producer/**")
				.hasRole("USER").antMatchers(HttpMethod.POST, "/javainuse-kafka/producer").hasRole("ADMIN")
				.antMatchers(HttpMethod.PUT, "/javainuse-kafka/producer/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.PATCH, "/javainuse-kafka/producer/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE, "/javainuse-kafka/producer/**").hasRole("ADMIN").and().csrf().disable()
				.formLogin().disable();
	}

	/*
	 * @Bean public UserDetailsService userDetailsService() { //ok for demo
	 * User.UserBuilder users = User.withDefaultPasswordEncoder();
	 * 
	 * InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
	 * manager.createUser(users.username("user").password("password").roles("USER").
	 * build());
	 * manager.createUser(users.username("admin").password("password").roles("USER",
	 * "ADMIN").build()); return manager; }
	 */

}
