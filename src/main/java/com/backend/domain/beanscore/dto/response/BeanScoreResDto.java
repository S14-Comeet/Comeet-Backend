package com.backend.domain.beanscore.dto.response;

import java.util.List;

import com.backend.domain.bean.enums.RoastingLevel;

/**
 * 원두 점수 응답 DTO
 */
public record BeanScoreResDto(
	Long id,
	Long beanId,
	Integer acidity,
	Integer body,
	Integer sweetness,
	Integer bitterness,
	Integer aroma,
	Integer flavor,
	Integer aftertaste,
	Integer totalScore,
	RoastingLevel roastLevel,
	List<String> flavorTags
) {
}
