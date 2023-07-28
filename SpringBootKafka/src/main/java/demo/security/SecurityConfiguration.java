package demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * This is new way to configure security. The old way is deprecated
 * https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
 * https://www.baeldung.com/spring-deprecated-websecurityconfigureradapter
 * https://www.codejava.net/frameworks/spring/encoded-password-does-not-look-like-bcrypt
 * @author vikasgond
 *
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

	/*
	 * @Bean public UserDetailsService userDetailsService() { // ok for demo
	 * User.UserBuilder users = User.withDefaultPasswordEncoder();
	 * 
	 * InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
	 * manager.createUser(users.username("user").password(passwordEncoder().encode(
	 * "password")).roles("USER").build()); manager.createUser(
	 * users.username("admin").password(passwordEncoder().encode("password")).roles(
	 * "USER", "ADMIN").build()); return manager; }
	 */

	@Bean
	public UserDetailsService userDetailsService(BCryptPasswordEncoder passwordEncoder) {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(
				User.withUsername("user").password(passwordEncoder.encode("password")).roles("USER").build());
		manager.createUser(
				User.withUsername("admin").password(passwordEncoder.encode("password")).roles("USER", "ADMIN").build());
		return manager;
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http// HTTP Basic authentication
			// .authenticationProvider(authenticationProvider())
				.httpBasic().and().authorizeRequests().antMatchers(HttpMethod.GET, "/javainuse-kafka/producer/**")
				.hasRole("USER").antMatchers(HttpMethod.POST, "/javainuse-kafka/producer").hasRole("ADMIN")
				.antMatchers(HttpMethod.PUT, "/javainuse-kafka/producer/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.PATCH, "/javainuse-kafka/producer/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE, "/javainuse-kafka/producer/**").hasRole("ADMIN").and().csrf().disable()
				.formLogin().disable();

		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
	}

	// Or, given our UserDetailService, we can even set an AuthenticationManager:
	/*
	 * @Bean public AuthenticationManager authenticationManager(HttpSecurity http,
	 * BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService
	 * userDetailServices) throws Exception { return
	 * http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(
	 * userDetailsServices) .passwordEncoder(bCryptPasswordEncoder).and().build(); }
	 */

}
