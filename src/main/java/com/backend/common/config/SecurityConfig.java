package com.backend.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CorsConfig corsFilter;

	private static final String[] WHITELIST = {
		"/swagger-ui/**",
		"/v3/api-docs/**",
		"/error"
	};

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.logout(AbstractHttpConfigurer::disable)
			.sessionManagement(SecurityConfig::createSessionPolicy)
			.addFilterBefore(corsFilter.corsFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	private static void createSessionPolicy(SessionManagementConfigurer<HttpSecurity> session) {
		session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
}