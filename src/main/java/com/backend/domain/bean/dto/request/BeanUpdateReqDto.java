package com.backend.domain.bean.dto.request;

import java.util.List;

import com.backend.domain.bean.enums.RoastingLevel;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

@Schema(description = "원두 수정 요청 DTO")
public record BeanUpdateReqDto(
	@Schema(description = "이름", example = "로우키 블렌드", requiredMode = RequiredMode.NOT_REQUIRED)
	String name,

	@Schema(description = "생산 국가", example = "에티오피아", requiredMode = RequiredMode.NOT_REQUIRED)
	String country,

	@Schema(description = "농장 이름", example = "예가체프 코체레", requiredMode = RequiredMode.NOT_REQUIRED)
	String farm,

	@Schema(description = "품종", example = "헤이룸", requiredMode = RequiredMode.NOT_REQUIRED)
	String variety,

	@Schema(description = "가공 방식", example = "워시드", requiredMode = RequiredMode.NOT_REQUIRED)
	String processingMethod,

	@Schema(description = "로스팅 레벨", example = "LIGHT", requiredMode = RequiredMode.NOT_REQUIRED)
	RoastingLevel roastingLevel,

	@Schema(description = "Flavor ID 리스트", example = "[1, 5, 10]", requiredMode = RequiredMode.NOT_REQUIRED)
	List<Long> flavorIds
) {
}
