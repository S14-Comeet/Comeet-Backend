package com.backend.common.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import jakarta.servlet.http.Cookie;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CookieUtil {

	private static final String REFRESH_TOKEN_NAME = "refreshToken";
	private static final String PATH = "/";

	public Cookie generateCookie(final String refreshToken, final Long refreshTokenExpiration) {
		Cookie cookie = new Cookie(REFRESH_TOKEN_NAME, refreshToken);
		cookie.setPath(PATH);
		ZonedDateTime seoulTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
		ZonedDateTime expirationTime = seoulTime.plusSeconds(refreshTokenExpiration);
		cookie.setMaxAge((int)(expirationTime.toEpochSecond() - seoulTime.toEpochSecond()));
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		return cookie;
	}

	public Cookie deleteRefreshCookie(final String refreshToken) {
		Cookie cookie = new Cookie(REFRESH_TOKEN_NAME, refreshToken);
		cookie.setPath(PATH);
		cookie.setMaxAge(0);
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		return cookie;
	}
}
