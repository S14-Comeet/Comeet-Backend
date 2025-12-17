package com.backend.common.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

	private static final String REGEX = ",";
	@Value("${cors.allowed-origins}")
	private String allowedOrigins;

	private static final List<String> ALLOWED_HEADERS = List.of(
		"Authorization",
		"Content-Type",
		"Accept",
		"Origin",
		"Access-Control-Request-Method",
		"Access-Control-Request-Headers",
		"X-Requested-With",
		"accessToken",
		"refreshToken",
		"EnvType"
	);

	private static final List<String> ALLOWED_METHODS = List.of(
		"GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"
	);

	private static final List<String> EXPOSED_HEADERS = List.of(
		"accessToken",
		"Authorization",
		"refreshToken",
		"Set-Cookie",
		"Access-Control-Allow-Origin",
		"Access-Control-Allow-Credentials"
	);

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);

		List<String> origins = Arrays.stream(allowedOrigins.split(REGEX))
			.map(String::trim)
			.toList();
		config.setAllowedOrigins(origins);

		config.setAllowedHeaders(ALLOWED_HEADERS);
		config.setAllowedMethods(ALLOWED_METHODS);
		config.setExposedHeaders(EXPOSED_HEADERS);
		config.setMaxAge(3600L);

		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}
