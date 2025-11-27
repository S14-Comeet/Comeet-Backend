package com.backend.domain.auth.service.command;

import org.springframework.stereotype.Service;

import com.backend.common.auth.constants.AuthConstant;
import com.backend.common.auth.dto.Token;
import com.backend.common.auth.jwt.JwtProperties;
import com.backend.common.auth.jwt.JwtTokenProvider;
import com.backend.common.auth.redis.RefreshToken;
import com.backend.common.auth.redis.repository.BlackListRepository;
import com.backend.common.auth.redis.repository.RefreshTokenRepository;
import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.AuthException;
import com.backend.common.error.exception.BusinessException;
import com.backend.common.error.exception.UserException;
import com.backend.common.util.CookieUtil;
import com.backend.domain.user.entity.User;
import com.backend.domain.user.service.command.UserCommandService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthCommandServiceImpl implements AuthCommandService {

	private final JwtProperties jwtProperties;
	private final JwtTokenProvider jwtTokenProvider;
	private final BlackListRepository blackListRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final UserCommandService userCommandService;

	@Override
	public void logout(
		final HttpServletRequest request,
		final HttpServletResponse response
	) {
		String refreshToken = CookieUtil.extractRefreshToken(request);

		User user = jwtTokenProvider.getUser(refreshToken)
			.orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

		String accessToken = jwtTokenProvider.extractAccessToken(request)
			.orElseThrow(() -> new AuthException(ErrorCode.TOKEN_NOT_FOUND));

		jwtTokenProvider.setBlackList(refreshToken);
		jwtTokenProvider.setBlackList(accessToken);
		refreshTokenRepository.deleteById(user.getSocialId());

		Cookie cookie = CookieUtil.deleteRefreshCookie(refreshToken);
		response.addCookie(cookie);
	}

	@Override
	public void reissue(final HttpServletRequest request, final HttpServletResponse response) {
		String refreshToken = CookieUtil.extractRefreshToken(request);

		if (blackListRepository.existsById(refreshToken)) {
			throw new BusinessException(ErrorCode.TOKEN_BLACKLISTED_EXCEPTION);
		}

		User user = jwtTokenProvider.getUser(refreshToken)
			.orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

		RefreshToken savedRefreshToken = refreshTokenRepository.findById(user.getSocialId())
			.orElseThrow(() -> new AuthException(ErrorCode.TOKEN_NOT_FOUND));

		if (!refreshToken.equals(savedRefreshToken.getToken())) {
			throw new AuthException(ErrorCode.REFRESH_TOKEN_NOT_MATCH);
		}

		jwtTokenProvider.setBlackList(refreshToken);

		Token token = jwtTokenProvider.createToken(user);
		savedRefreshToken.updateToken(token.refreshToken());

		refreshTokenRepository.save(savedRefreshToken);

		response.addHeader(AuthConstant.AUTHORIZATION, AuthConstant.BEARER + token.accessToken());
		Cookie cookie = CookieUtil.generateCookie(savedRefreshToken.toString(),
			jwtProperties.refreshTokenExpiration());
		response.addCookie(cookie);
	}

	@Override
	public void updateRole(final String socialId, final com.backend.domain.user.entity.Role role) {
		User user = jwtTokenProvider.getAuthenticatedUser(socialId).getUser();
		user.updateRole(role);
		userCommandService.save(user);
		log.info("[Auth] User role updated to: {} for socialId: {}", role, socialId);
	}
}
