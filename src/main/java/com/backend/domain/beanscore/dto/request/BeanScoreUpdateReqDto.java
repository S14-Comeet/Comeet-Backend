package com.backend.domain.beanscore.dto.request;

import java.util.List;

import com.backend.domain.bean.enums.RoastingLevel;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * 원두 점수 업데이트 요청 DTO
 */
public record BeanScoreUpdateReqDto(
	@Min(1) @Max(10)
	Integer acidity,

	@Min(1) @Max(10)
	Integer body,

	@Min(1) @Max(10)
	Integer sweetness,

	@Min(1) @Max(10)
	Integer bitterness,

	@Min(1) @Max(10)
	Integer aroma,

	@Min(1) @Max(10)
	Integer flavor,

	@Min(1) @Max(10)
	Integer aftertaste,

	@Min(0) @Max(100)
	Integer totalScore,

	RoastingLevel roastLevel,

	List<String> flavorTags
) {
}
