package com.backend.common.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.backend.common.auth.handler.OAuth2LoginSuccessHandler;
import com.backend.common.auth.service.CustomOAuth2UserService;
import com.backend.common.filter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CorsConfig corsConfig;
	private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final CustomOAuth2UserService customOAuth2UserService;

	private static final String[] WHITELIST = {
		// Web
		"/",
		"/error",
		"/favicon.ico",

		// Swagger
		"/v3/api-docs/**",
		"/swagger-ui/**",
		"/swagger-resources/**",

		// Actuator
		"/actuator",
		"/actuator/**",

		// OAuth2
		"/oauth2/**",
		"/login/**",

		// Auth
		"/auth/reissue",
	};

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.logout(AbstractHttpConfigurer::disable)
			.sessionManagement(SecurityConfig::createSessionPolicy);

		http
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
				.requestMatchers(WHITELIST).permitAll()

				.requestMatchers(HttpMethod.GET, "/stores/").permitAll()
				.requestMatchers(HttpMethod.GET, "/stores/*").permitAll()
				.requestMatchers(HttpMethod.GET, "/stores/*/menus").permitAll()
				.requestMatchers(HttpMethod.GET, "/stores/*/reviews").permitAll()
				.requestMatchers(HttpMethod.GET, "/menus/*").permitAll()
				.requestMatchers(HttpMethod.GET, "/flavors").permitAll()
				.requestMatchers(HttpMethod.GET, "/beans/*").permitAll()

				.anyRequest().authenticated()
			).oauth2Login(oauth2 -> oauth2
				.userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
				.successHandler(oAuth2LoginSuccessHandler));

		http
			.addFilterBefore(corsConfig.corsFilter(), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	private static void createSessionPolicy(SessionManagementConfigurer<HttpSecurity> session) {
		session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
}