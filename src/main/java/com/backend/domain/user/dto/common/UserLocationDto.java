package com.backend.domain.user.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Schema(description = "사용자 현재 위치 정보 DTO")
public record UserLocationDto(
	@Schema(description = "사용자 현재 위도 좌표 (대한민국 범위: 33.0 ~ 38.6)", example = "37.5665")
	@NotNull(message = "사용자 위도는 필수 입력값입니다.")
	@DecimalMin(value = "33.0", message = "위도는 33.0 이상이어야 합니다.")
	@DecimalMax(value = "38.6", message = "위도는 38.6 이하여야 합니다.")
	Double latitude,

	@Schema(description = "사용자 현재 경도 좌표 (대한민국 범위: 124.0 ~ 131.9)", example = "126.9780")
	@NotNull(message = "사용자 경도는 필수 입력값입니다.")
	@DecimalMin(value = "124.0", message = "경도는 124.0 이상이어야 합니다.")
	@DecimalMax(value = "131.9", message = "경도는 131.9 이하여야 합니다.")
	Double longitude
) {
}
