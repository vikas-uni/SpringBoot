package profesorp.oauth2.resourceServer;



import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration // this annotation is needed for this class to be read as configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter
{
	  @Override
		public void configure(HttpSecurity http) throws Exception {
			http
			.authorizeRequests().antMatchers("/oauth/token", "/oauth/authorize**", "/publica").permitAll();
//			 .anyRequest().authenticated();
			http.requestMatchers().antMatchers("/privada")
			.and().authorizeRequests()
			.antMatchers("/privada").access("hasRole('USER')")
			.and().requestMatchers().antMatchers("/admin")
			.and().authorizeRequests()
			.antMatchers("/admin").access("hasRole('ADMIN')");
		}   

}

