package com.backend.domain.bean.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.backend.domain.bean.enums.RoastingLevel;
import com.backend.domain.flavor.dto.common.FlavorBadgeDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "원두 응답 DTO")
public record BeanResDto(
	@Schema(description = "원두 ID", example = "1")
	Long id,

	@Schema(description = "로스터리 ID", example = "1")
	Long roasteryId,

	@Schema(description = "생산 국가", example = "에티오피아")
	String country,

	@Schema(description = "농장 이름", example = "예가체프 코체레")
	String farm,

	@Schema(description = "품종", example = "헤이룸")
	String variety,

	@Schema(description = "가공 방식", example = "워시드")
	String processingMethod,

	@Schema(description = "로스팅 레벨", example = "LIGHT")
	RoastingLevel roastingLevel,

	@Schema(description = "Flavor 리스트")
	List<FlavorBadgeDto> flavors,

	@Schema(description = "생성 일시", example = "2025-01-01T00:00:00")
	LocalDateTime createdAt,

	@Schema(description = "수정 일시", example = "2025-01-15T12:30:00")
	LocalDateTime updatedAt
) {
}
