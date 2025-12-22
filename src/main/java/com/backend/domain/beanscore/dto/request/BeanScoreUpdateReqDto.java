package com.backend.domain.beanscore.dto.request;

import com.backend.domain.bean.enums.RoastingLevel;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * 원두 점수 업데이트 요청 DTO
 */
@Schema(description = "원두 점수 업데이트 요청")
public record BeanScoreUpdateReqDto(
	@Schema(description = "산미 (1-10)", example = "7")
	@Min(1) @Max(10)
	Integer acidity,

	@Schema(description = "바디감 (1-10)", example = "5")
	@Min(1) @Max(10)
	Integer body,

	@Schema(description = "단맛 (1-10)", example = "6")
	@Min(1) @Max(10)
	Integer sweetness,

	@Schema(description = "쓴맛 (1-10)", example = "4")
	@Min(1) @Max(10)
	Integer bitterness,

	@Schema(description = "향 (1-10)", example = "8")
	@Min(1) @Max(10)
	Integer aroma,

	@Schema(description = "풍미 (1-10)", example = "7")
	@Min(1) @Max(10)
	Integer flavor,

	@Schema(description = "여운 (1-10)", example = "6")
	@Min(1) @Max(10)
	Integer aftertaste,

	@Schema(description = "총점 (0-100)", example = "85")
	@Min(0) @Max(100)
	Integer totalScore,

	@Schema(description = "로스팅 레벨", example = "LIGHT")
	RoastingLevel roastLevel
) {
}
