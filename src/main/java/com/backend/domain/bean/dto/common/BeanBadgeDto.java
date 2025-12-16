package com.backend.domain.bean.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "원두 뱃지 정보 DTO")
public record BeanBadgeDto(
	@Schema(description = "원두 ID", example = "1")
	Long id,

	@Schema(description = "원두 이름", example = "에티오피아 카마샤")
	String name
) {
}
