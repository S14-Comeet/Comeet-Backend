package com.backend.domain.beanscore.dto.response;

import java.util.List;

import com.backend.domain.bean.enums.RoastingLevel;

/**
 * 원두 정보와 함께 반환하는 원두 점수 DTO
 */
public record BeanScoreWithBeanDto(
	// Bean 정보
	Long beanId,
	String beanName,
	String country,
	String roasteryName,

	// Score 정보
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
