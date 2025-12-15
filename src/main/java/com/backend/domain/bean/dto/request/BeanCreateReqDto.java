package com.backend.domain.bean.dto.request;

import com.backend.domain.bean.enums.RoastingLevel;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "원두 생성 요청 DTO")
public record BeanCreateReqDto(
	@Schema(description = "로스터리 ID", example = "1", requiredMode = RequiredMode.REQUIRED)
	@NotNull(message = "로스터리 ID는 필수 입력값입니다.")
	Long roasteryId,

	@Schema(description = "생산 국가", example = "에티오피아", requiredMode = RequiredMode.REQUIRED)
	@NotBlank(message = "생산 국가는 필수 입력값입니다.")
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
	java.util.List<Long> flavorIds
) {
}
