package com.backend.domain.visit.dto.common;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "방문 인증 목록 조회 응답 DTO")
public record VisitPageDto(
	@Schema(description = "방문 인증 ID", example = "1")
	Long visitId,

	@Schema(description = "인증한 메뉴 ID", example = "10")
	Long menuId,

	@Schema(description = "인증 성공 여부", example = "true")
	Boolean verified,

	@Schema(description = "방문 인증 일시", example = "2024-12-07T14:30:00")
	LocalDateTime visitedAt
) {
}
