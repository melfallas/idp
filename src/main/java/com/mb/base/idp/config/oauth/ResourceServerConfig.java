package com.mb.base.idp.config.oauth;

import java.util.Arrays;
import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends DefaultResourceServer {
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers(
				HttpMethod.GET,
				"/actuator/health**",
				"/api/test1",
				"/api/test3"
				).permitAll()
		.antMatchers(
				HttpMethod.POST,
				"/api/auth/pass-client-login"
				).permitAll()
		.anyRequest().authenticated()
		.and().cors()
		.configurationSource(corsConfigurationSource())
		;
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		// Creating CORS configuration
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("*"));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowCredentials(true);
		config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
		// Register CORS configuration
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
	
}
