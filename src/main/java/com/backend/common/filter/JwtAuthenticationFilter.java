package com.backend.common.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
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

/**
 * JWT 인증 필터
 *
 * <p>이 필터는 Authorization 헤더에 Bearer 토큰이 있는 경우에만 JWT를 검증합니다.
 * 인가(Authorization) 정책은 SecurityConfig에서 일원화하여 관리합니다.</p>
 *
 * <p>동작 방식:</p>
 * <ul>
 *   <li>Authorization 헤더가 없음 → 그대로 통과 (SecurityConfig에서 인가 처리)</li>
 *   <li>Authorization 헤더가 있음 → JWT 검증 후 인증 정보 설정</li>
 * </ul>
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		final String authorizationHeader = request.getHeader(AuthConstant.AUTHORIZATION);

		// Authorization 헤더가 있고 Bearer로 시작하면 JWT 검증 및 인증 정보 설정
		if (authorizationHeader != null && authorizationHeader.startsWith(AuthConstant.BEARER)) {
			final String bearerToken = getBearerToken(authorizationHeader);
			jwtTokenProvider.validateToken(bearerToken);
			setAuthentication(bearerToken);
		}

		// SecurityConfig의 authorizeHttpRequests에서 인가 처리
		filterChain.doFilter(request, response);
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
