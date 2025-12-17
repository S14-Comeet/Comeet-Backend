package com.backend.domain.menu.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "메뉴-원두 매핑 응답 DTO")
public record MenuBeanMappingResDto(
	@Schema(description = "메뉴 ID", example = "1")
	Long menuId,

	@Schema(description = "원두 ID", example = "1")
	Long beanId,

	@Schema(description = "블렌드 여부", example = "false")
	boolean isBlended,

	@Schema(description = "매핑 여부", example = "true")
	boolean isConnected
) {
}
