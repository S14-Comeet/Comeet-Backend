package com.backend.domain.flavor.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Flavor 뱃지 정보 DTO")
public record FlavorBadgeDto(
	@Schema(description = "Flavor ID", example = "1")
	Long flavorId,

	@Schema(description = "Flavor 코드 (영문명)", example = "Fruity")
	String code,

	@Schema(description = "Flavor 색상 Hex 코드", example = "#FF5733")
	String colorHex
) {
}
