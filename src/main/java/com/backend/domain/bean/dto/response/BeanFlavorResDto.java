package com.backend.domain.bean.dto.response;

import java.util.List;

import com.backend.domain.flavor.dto.common.FlavorBadgeDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "원두-플레이버 매핑 응답 DTO")
public record BeanFlavorResDto(
	@Schema(description = "원두 ID", example = "1")
	Long beanId,

	@Schema(description = "매핑된 Flavor 리스트")
	List<FlavorBadgeDto> flavors
) {
}
