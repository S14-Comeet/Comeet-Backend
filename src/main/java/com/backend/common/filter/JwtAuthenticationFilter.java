package com.backend.common.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.backend.common.auth.constants.AuthConstant;
import com.backend.common.auth.jwt.JwtTokenProvider;
import com.backend.common.auth.principal.AuthenticatedUser;
import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.AuthException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final AntPathMatcher pathMatcher = new AntPathMatcher();

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
	};

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String requestURI = request.getRequestURI();

		// WHITELIST 경로는 JWT 검증 스킵
		if (isWhitelisted(requestURI)) {
			filterChain.doFilter(request, response);
			return;
		}

		final String authorizationHeader = request.getHeader(AuthConstant.AUTHORIZATION);

		// Authorization 헤더가 없거나 Bearer로 시작하지 않으면 필터 통과
		// (SecurityConfig의 authenticated()가 처리)
		if (authorizationHeader == null || !authorizationHeader.startsWith(AuthConstant.BEARER)) {
			filterChain.doFilter(request, response);
			return;
		}

		final String bearerToken = getBearerToken(authorizationHeader);
		jwtTokenProvider.validateToken(bearerToken);
		setAuthentication(bearerToken);

		filterChain.doFilter(request, response);
	}

	private boolean isWhitelisted(String requestURI) {
		return Arrays.stream(WHITELIST)
			.anyMatch(pattern -> pathMatcher.match(pattern, requestURI));
	}

	private void setAuthentication(String accessToken) {
		AuthenticatedUser authenticatedUser = jwtTokenProvider.getAuthenticatedUser(accessToken);
		Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
			authenticatedUser,
			"",
			List.of(new SimpleGrantedAuthority(authenticatedUser.getRole().getKey()))
		);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}

	private String getBearerToken(String authorizationHeader) {
		if (authorizationHeader == null || !authorizationHeader.startsWith(AuthConstant.BEARER)) {
			throw new AuthException(ErrorCode.MALFORMED_TOKEN_EXCEPTION);
		}
		return authorizationHeader.replace(AuthConstant.BEARER, "");
	}

}
