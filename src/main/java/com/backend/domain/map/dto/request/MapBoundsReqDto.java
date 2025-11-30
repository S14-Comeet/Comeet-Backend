package com.backend.domain.map.dto.request;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "지도 범위 검색 요청 DTO")
public record MapBoundsReqDto(
	@Schema(description = "사용자 위도", example = "37.5665", required = true)
	BigDecimal latitude,

	@Schema(description = "사용자 경도", example = "126.9780", required = true)
	BigDecimal longitude,

	@Schema(description = "최대 거리 (km), 미입력 시 기본값 적용", example = "1.0")
	Double maxDistance
) {
	public Double getMaxDistance() {
		return maxDistance != null ? maxDistance : 1.0;
	}
}
