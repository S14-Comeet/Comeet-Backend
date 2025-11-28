package com.backend.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record CheckNicknameDuplicateResponse(
	@Schema(title = "확인한 닉네임")
	String nickname,
	@Schema(title = "닉네임 사용 가능 여부")
	Boolean available
) {
}
