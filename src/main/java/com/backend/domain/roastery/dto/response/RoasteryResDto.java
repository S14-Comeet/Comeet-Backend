package com.backend.domain.roastery.dto.response;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "로스터리 응답 DTO")
public record RoasteryResDto(
	@Schema(description = "로스터리 ID", example = "1")
	Long id,

	@Schema(description = "로스터리 관리자 ID", example = "123")
	Long ownerId,

	@Schema(description = "로스터리 이름", example = "블루보틀 코리아")
	String name,

	@Schema(description = "로고 이미지 URL", example = "https://example.com/logo.png")
	String logoUrl,

	@Schema(description = "웹사이트 URL", example = "https://bluebottlecoffee.kr")
	String websiteUrl,

	@Schema(description = "생성 일시", example = "2025-01-01T00:00:00")
	LocalDateTime createdAt,

	@Schema(description = "수정 일시", example = "2025-01-15T12:30:00")
	LocalDateTime updatedAt
) {
}
