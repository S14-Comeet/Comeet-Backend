package com.backend.domain.user.dto.request;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 정보 수정 요청 DTO")
public record UserUpdateReqDto(
	@Schema(
		description = "사용자 닉네임 (1~12자)",
		example = "새닉네임",
		minLength = 1,
		maxLength = 12
	)
	@Length(min = 1, max = 12, message = "닉네임은 1자 이상 12자 이하여야 합니다.")
	String nickname,

	@Schema(
		description = "프로필 이미지 URL",
		example = "https://example.com/new-profile.jpg"
	)
	String profileImageUrl
) {
}
