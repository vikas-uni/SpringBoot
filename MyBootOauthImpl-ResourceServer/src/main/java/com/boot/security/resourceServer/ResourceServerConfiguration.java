package com.boot.security.resourceServer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration // this annotation is needed for this class to be read as configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
	private static final String RESOURCE_ID = "resource-server-rest-api";

	@Value("${jwt.secret.key}")
	private String key;// = "as466gf";
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID);
	}
	
	//these beans for token store are needed when we separate resource server and auth server
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter());
	}
	
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(key);
		return converter;
	}
	
	//this bean is not in use because we are using JWT in this example currently
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new  BCryptPasswordEncoder();
    }
   

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/oauth/token", "/oauth/authorize**", "/publicApi").permitAll();
//			 .anyRequest().authenticated();
		http.requestMatchers().antMatchers("/privateApi", "/testApi") // the /testApi id not checked for authentication
																		// by spring security but by ResourceServer
				.and().authorizeRequests().antMatchers("/privateApi").access("hasRole('USER')").antMatchers("/testApi")
				.access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')").and().requestMatchers().antMatchers("/admin").and().authorizeRequests()
				.antMatchers("/admin").access("hasRole('ADMIN')");
	}

	// below config works based on read, write scopes defined in DB in
	// OAUTH_CLIENT_DETAILS

//	  private static final String SECURED_READ_SCOPE = "#oauth2.hasScope('read')";
//	    private static final String SECURED_WRITE_SCOPE = "#oauth2.hasScope('write')";
//	    private static final String SECURED_PATTERN = "/secured/**";

//	  @Override
//	    public void configure(HttpSecurity http) throws Exception {
//	        http.requestMatchers()
//	                .antMatchers(SECURED_PATTERN).and().authorizeRequests()
//	                .antMatchers(HttpMethod.POST, SECURED_PATTERN).access(SECURED_WRITE_SCOPE)
//	                .anyRequest().access(SECURED_READ_SCOPE);
//	    }

}
