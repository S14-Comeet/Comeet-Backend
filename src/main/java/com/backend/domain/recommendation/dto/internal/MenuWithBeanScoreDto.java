package com.backend.domain.recommendation.dto.internal;

import java.math.BigDecimal;
import java.util.List;

import com.backend.domain.bean.enums.RoastingLevel;

/**
 * 메뉴와 원두 점수 정보를 함께 담는 DTO (쿼리 결과 매핑용)
 */
public record MenuWithBeanScoreDto(
	// 메뉴 정보
	Long menuId,
	String menuName,
	String menuDescription,
	Integer menuPrice,
	String menuImageUrl,

	// 카페 정보
	Long storeId,
	String storeName,
	String storeAddress,
	BigDecimal storeLatitude,
	BigDecimal storeLongitude,

	// 원두 정보
	Long beanId,
	String beanName,
	String beanCountry,
	String roasteryName,

	// 원두 점수 정보
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
