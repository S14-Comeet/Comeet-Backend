package com.backend.domain.visit.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "방문 인증 응답 DTO")
public record VerifiedResDto(
	@Schema(description = "생성된 방문 인증 ID", example = "1")
	Long visitId,

	@Schema(description = "인증 성공 여부 (true: 100m 이내 인증 성공, false: 100m 초과 인증 실패)", example = "true")
	Boolean isVerified
) {
}
