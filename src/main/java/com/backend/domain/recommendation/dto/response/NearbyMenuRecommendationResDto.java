package com.backend.domain.recommendation.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "근거리 메뉴 추천 응답 DTO (반경 확장 정보 포함)")
public record NearbyMenuRecommendationResDto(
	@Schema(description = "추천 메뉴 목록")
	List<MenuRecommendationResDto> recommendations,

	@Schema(description = "요청한 반경 (km)", example = "5")
	Integer requestedRadiusKm,

	@Schema(description = "실제 검색된 반경 (km)", example = "10")
	Integer actualRadiusKm,

	@Schema(description = "반경 확장 여부", example = "true")
	Boolean radiusExpanded
) {
}
