package com.boot.security.authServer;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	// create the required tables in the DB and autowire the DataSource object
	// this will fetch details for oauth clients from DB automatically
	// pass this object to token store and ClientDetailsServiceConfigurer
	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	@Qualifier("userService")
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder oauthClientPasswordEncoder;
	
	@Value("${jwt.secret.key}")
	private String key;// = "as466gf";

//	@Bean
//	public TokenStore tokenStore() {
//		return new JdbcTokenStore(dataSource);
//	}
	
	//------------add these lines to get jwt token instead of normal token-----
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
	//----------------------------------

	@Bean
	public OAuth2AccessDeniedHandler oauthAccessDeniedHandler() {
		return new OAuth2AccessDeniedHandler();
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()")
				.passwordEncoder(oauthClientPasswordEncoder);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource);
	}

//	@Override
//	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
//		endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager)
//				.userDetailsService(userDetailsService);
//	}
	
	//for jwt token, add- tokenEnhancer(jwtAccessTokenConverter())
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints.tokenStore(tokenStore()).
		tokenEnhancer(jwtAccessTokenConverter()).
		authenticationManager(authenticationManager)
				.userDetailsService(userDetailsService);
	}
}
