package com.backend.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "닉네임 중복 체크 응답 DTO")
public record NicknameDuplicateResDto(
	@Schema(description = "확인한 닉네임", example = "김싸피")
	String nickname,
	@Schema(description = "닉네임 중복 여부 (true: 이미 사용중, false: 사용 가능)", example = "false")
	boolean exists
) {
}
