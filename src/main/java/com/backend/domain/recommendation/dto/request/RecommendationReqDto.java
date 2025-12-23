package com.backend.domain.recommendation.dto.request;

import java.math.BigDecimal;

import com.backend.domain.recommendation.enums.RecommendationType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Schema(description = "추천 요청 DTO")
public record RecommendationReqDto(
	@Schema(description = "추천 유형 (LOCAL: 근거리, GLOBAL: 전역)", example = "LOCAL", defaultValue = "GLOBAL")
	RecommendationType type,

	@Schema(description = "사용자 위도 (LOCAL 모드 시 필수)", example = "37.5665")
	@DecimalMin(value = "-90.0", message = "위도는 -90 이상이어야 합니다")
	@DecimalMax(value = "90.0", message = "위도는 90 이하여야 합니다")
	BigDecimal latitude,

	@Schema(description = "사용자 경도 (LOCAL 모드 시 필수)", example = "126.9780")
	@DecimalMin(value = "-180.0", message = "경도는 -180 이상이어야 합니다")
	@DecimalMax(value = "180.0", message = "경도는 180 이하여야 합니다")
	BigDecimal longitude,

	@Schema(description = "검색 반경 (km, LOCAL 모드 시 사용)", example = "5", defaultValue = "5")
	@Min(value = 1, message = "반경은 1km 이상이어야 합니다")
	@Max(value = 50, message = "반경은 50km 이하여야 합니다")
	Integer radiusKm
) {
	public RecommendationReqDto {
		if (type == null) {
			type = RecommendationType.GLOBAL;
		}
		if (radiusKm == null) {
			radiusKm = 5;
		}
	}

	public boolean isLocal() {
		return type == RecommendationType.LOCAL;
	}

	public boolean hasValidLocation() {
		return latitude != null && longitude != null;
	}
}
