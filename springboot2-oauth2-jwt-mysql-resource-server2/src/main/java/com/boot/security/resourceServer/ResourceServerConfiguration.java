package com.boot.security.resourceServer;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
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
	private static final String RESOURCE_ID = "USER_ADMIN_RESOURCE";

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID).tokenStore(tokenStore());
	}
	
	//these beans for token store are needed when we separate resource server and auth server
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter());
	}
	
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		 JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		    Resource resource = new ClassPathResource("pubkey2.pem");
		    String publicKey = null;
		    try {
		        publicKey = IOUtils.toString(resource.getInputStream());
		    } catch (final IOException e) {
		        throw new RuntimeException(e);
		    }
		    converter.setVerifierKey(publicKey);
		    return converter;
	}
	
	//this bean is not in use because we are using JWT in this example currently
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new  BCryptPasswordEncoder();
    }
   

	// this configuration is currently not working based on roles
	/*
	 * @Override public void configure(HttpSecurity http) throws Exception {
	 * //http.authorizeRequests().antMatchers("/oauth/token", "/oauth/authorize**",
	 * "/publicApi").permitAll(); http.requestMatchers().antMatchers("/privateApi",
	 * "/testApi") // the /testApi is not checked for authentication // // by spring
	 * security but by ResourceServer
	 * .and().authorizeRequests().antMatchers("/privateApi").access(
	 * "hasRole('role_admin')").antMatchers("/testApi")
	 * .access("hasRole('ADMIN')").and().requestMatchers().antMatchers("/admin").and
	 * ().authorizeRequests() .antMatchers("/admin").access("hasRole('ADMIN')"); }
	 */
	 
	//this configuration works but without roles
//	@Override
//	public void configure(HttpSecurity http) throws Exception {
//	    http.authorizeRequests()
//	      .antMatchers("/publicApi").permitAll()
//	      .anyRequest()
//	      .authenticated()
//	      ;
//	    
//	    http.authorizeRequests()
//	      .antMatchers("/privateApi").hasAuthority("admin") // this admin property doesnt makes any difference in access
//	      .anyRequest()
//	      .authenticated()
//	      ;
//	  }
	 

	// below config works based on scopes defined in the jwt

	  private static final String SECURED_READ_SCOPE = "#oauth2.hasScope('role_admin')";
	    private static final String SECURED_WRITE_SCOPE = "#oauth2.hasScope('role_admin')";
	    private static final String SECURED_PATTERN = "/privateApi";

	  @Override
	    public void configure(HttpSecurity http) throws Exception {
	        http.requestMatchers()
	                .antMatchers(SECURED_PATTERN).and().authorizeRequests()
	                .antMatchers(HttpMethod.POST, SECURED_PATTERN).access(SECURED_WRITE_SCOPE)
	                .anyRequest().access(SECURED_READ_SCOPE);
	    }

}
