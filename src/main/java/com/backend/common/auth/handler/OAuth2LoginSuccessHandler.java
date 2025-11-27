package com.backend.common.auth.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.backend.common.auth.constants.AuthConstant;
import com.backend.common.auth.dto.CustomOAuth2User;
import com.backend.common.auth.dto.Token;
import com.backend.common.auth.jwt.JwtProperties;
import com.backend.common.auth.jwt.JwtTokenProvider;
import com.backend.common.auth.redis.RefreshToken;
import com.backend.common.auth.redis.repository.RefreshTokenRepository;
import com.backend.common.util.CookieUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtProperties jwtProperties;
	private final JwtTokenProvider jwtTokenProvider;
	private final RefreshTokenRepository refreshTokenRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {

		CustomOAuth2User oAuth2User = (CustomOAuth2User)authentication.getPrincipal();

		Token token = jwtTokenProvider.createToken(oAuth2User.getUser());
		response.addHeader(AuthConstant.AUTHORIZATION, AuthConstant.BEARER + token.accessToken());

		RefreshToken refreshToken = RefreshToken.builder()
			.socialId(oAuth2User.getUser().getSocialId())
			.token(token.refreshToken())
			.expirationTime(jwtProperties.refreshTokenExpiration())
			.build();

		refreshTokenRepository.save(refreshToken);

		Cookie cookie = CookieUtil.generateCookie(token.refreshToken(), jwtProperties.refreshTokenExpiration());
		response.addCookie(cookie);
		log.info("[Token] JWT 토큰 생성 및 발급 socialId : {}", oAuth2User.getUser().getSocialId());
		response.sendRedirect(AuthConstant.LOCAL_OAUTH_REDIRECT_URI);
	}
}
