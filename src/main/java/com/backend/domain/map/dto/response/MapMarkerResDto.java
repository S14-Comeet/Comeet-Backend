package com.backend.domain.map.dto.response;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "지도 마커 정보 응답 DTO")
public record MapMarkerResDto(
	@Schema(description = "매장 ID", example = "store-12345")
	String storeId,

	@Schema(description = "매장명", example = "커핏 강남점")
	String name,

	@Schema(description = "위도", example = "37.5665")
	BigDecimal latitude,

	@Schema(description = "경도", example = "126.9780")
	BigDecimal longitude,

	@Schema(description = "카테고리", example = "CAFE")
	String category,

	@Schema(description = "평균 평점", example = "4.5")
	BigDecimal averageRating,

	@Schema(description = "썸네일 URL", example = "https://example.com/thumbnail.jpg")
	String thumbnailUrl,

	@Schema(description = "사용자로부터의 거리 (km)", example = "1.0")
	Double distance,

	@Schema(description = "마커 색상 (영업 중: BLUE, 영업 안함: RED)", example = "BLUE", allowableValues = {"BLUE", "RED"})
	String markerColor
) {
}
