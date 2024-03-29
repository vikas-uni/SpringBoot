package com.example.authserver.web.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpointAuthenticationFilter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
@EnableAuthorizationServer
public class OAuth2Configuration extends AuthorizationServerConfigurerAdapter {

	@Value("${check-user-scopes}")
	private Boolean checkUserScopes;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private ClientDetailsService clientDetailsService;

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;
	
	
	/**
	 * Request factory.
	 * @author Mindbowser | deepak.kumbhar@mindbowser.com
     * @since Apr 28, 2020
	 * @return
	 */
	@Bean
	public OAuth2RequestFactory requestFactory() {
		CustomOauth2RequestFactory requestFactory = new CustomOauth2RequestFactory(clientDetailsService);
		requestFactory.setCheckUserScopes(true);
		return requestFactory;
	}
	
	
	/**
	 * Token store in datbase.
	 * @author Mindbowser | deepak.kumbhar@mindbowser.com
     * @since Apr 28, 2020
	 * @return
	 */
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter());
	}

	
	/**
	 * JWT access token converter.
	 * @author Mindbowser | deepak.kumbhar@mindbowser.com
     * @since Apr 28, 2020
	 * @return
	 */
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter converter = new CustomTokenEnhancer();
		//converter.setKeyPair(new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "password".toCharArray()).getKeyPair("jwt"));
		converter.setKeyPair(new KeyStoreKeyFactory(new ClassPathResource("mytest.jks"), "mypass".toCharArray()).getKeyPair("mytest"));
		return converter;
	}

	
	/**
	 * Configurer method to comunicate with DB.
	 * @author Mindbowser | deepak.kumbhar@mindbowser.com
     * @since Apr 28, 2020
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
	}
	
	
	/**
	 * Token end point authenticator.
	 * @author Mindbowser | deepak.kumbhar@mindbowser.com
     * @since Apr 28, 2020
	 * @return
	 */
	@Bean
	public TokenEndpointAuthenticationFilter tokenEndpointAuthenticationFilter() {
		return new TokenEndpointAuthenticationFilter(authenticationManager, requestFactory());
	}
	
	
	/**
	 * Check token access.
	 * @author Mindbowser | deepak.kumbhar@mindbowser.com
     * @since Apr 28, 2020
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}
	
	/**
	 * Check token scope.
	 * @author Mindbowser | deepak.kumbhar@mindbowser.com
     * @since Apr 28, 2020
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore()).tokenEnhancer(jwtAccessTokenConverter())
				.authenticationManager(authenticationManager).userDetailsService(userDetailsService);
		if (checkUserScopes)
			endpoints.requestFactory(requestFactory());
	}
	
}