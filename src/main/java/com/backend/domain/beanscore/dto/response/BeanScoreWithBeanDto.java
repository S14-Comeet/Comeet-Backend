package com.backend.domain.beanscore.dto.response;

import java.util.List;

import com.backend.domain.bean.enums.RoastingLevel;

/**
 * 원두 정보와 함께 반환하는 원두 점수 DTO
 * flavorTags는 bean_flavor_notes 테이블에서 조회됨
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

	// Flavor 정보 (bean_flavor_notes에서 조회)
	List<String> flavorTags
) {
}
