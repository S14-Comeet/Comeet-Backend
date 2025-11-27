package com.backend.common.auth.dto;

public record Token(
	String accessToken,
	String refreshToken
) {
}
