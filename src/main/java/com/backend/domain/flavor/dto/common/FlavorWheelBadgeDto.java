package com.backend.domain.flavor.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "FlavorWheel 뱃지 정보 DTO")
public record FlavorWheelBadgeDto(
	@Schema(description = "FlavorWheel ID", example = "1")
	Long flavorWheelId,

	@Schema(description = "FlavorWheel 코드 (영문명)", example = "Fruity")
	String code,

	@Schema(description = "FlavorWheel 색상 Hex 코드", example = "#FF5733")
	String colorHex
) {
}
