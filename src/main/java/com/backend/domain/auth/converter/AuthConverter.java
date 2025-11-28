package com.backend.domain.auth.converter;

import com.backend.domain.auth.dto.response.CheckNicknameDuplicateResponse;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthConverter {

	public static CheckNicknameDuplicateResponse toCheckNicknameDuplicateResponse(
		final String nickname,
		final Boolean available
	) {
		return CheckNicknameDuplicateResponse.builder()
			.nickname(nickname)
			.available(available)
			.build();
	}
}
