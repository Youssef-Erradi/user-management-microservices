package user.oauth.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter{
	@Value("${security.oauth2.client.clientId}")
	private String clientId;
	
	@Value("${security.oauth2.client.clientSecret}")
	private String clientSecret;
	
	@Value("${security.oauth2.client.signingKey}")
	private String signingKey;
	
	@Value("${security.oauth2.client.accessTokenValidity}")
	private int accessTokenValidity;
	
	
	@Value("${security.oauth2.client.refreshTokenValidity}")
	private int refreshTokenValidity;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Bean
	public JwtAccessTokenConverter tokenConverter() {
		var converter = new JwtAccessTokenConverter();
		converter.setSigningKey(signingKey);
		return converter;
	}
	
	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(tokenConverter());
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager)
				.tokenStore(tokenStore())
				.accessTokenConverter(tokenConverter());
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				.withClient(clientId)
				.secret(passwordEncoder.encode(clientSecret))
				.scopes("READ","WRITE")
				.authorizedGrantTypes("password","refresh_token")
				.accessTokenValiditySeconds(accessTokenValidity)
				.refreshTokenValiditySeconds(refreshTokenValidity);
	}

}
