package com.backend.domain.auth.converter;

import com.backend.domain.auth.dto.response.CheckNicknameDuplicateResponse;

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
