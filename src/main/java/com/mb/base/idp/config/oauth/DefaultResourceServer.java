package com.mb.base.idp.config.oauth;

import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

public class DefaultResourceServer extends ResourceServerConfigurerAdapter {
	
	@Value("${atp.client.secret}")
	private String signingKey;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/actuator/health/**").permitAll()
		.anyRequest().authenticated()
		;
	}
	
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    	resources.tokenServices(tokenService());
    }
    
    @Bean
    @Primary
    public DefaultTokenServices tokenService() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(signingKey);
        //converter.setVerifierKey(JwtConfig.LLAVE_SECRETA);
        return converter;
    }
    
}
