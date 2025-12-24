package com.backend.domain.recommendation.dto.internal;

import java.math.BigDecimal;
import java.util.List;

import com.backend.domain.bean.enums.RoastingLevel;

public record MenuWithBeanScoreDto(
	Long menuId,
	String menuName,
	String menuDescription,
	Integer menuPrice,
	String menuImageUrl,

	Long storeId,
	String storeName,
	String storeAddress,
	BigDecimal storeLatitude,
	BigDecimal storeLongitude,

	Long beanId,
	String beanName,
	String beanCountry,
	String roasteryName,

	Integer acidity,
	Integer body,
	Integer sweetness,
	Integer bitterness,
	Integer aroma,
	Integer flavor,
	Integer aftertaste,
	Integer totalScore,
	RoastingLevel roastLevel,
	List<String> flavorTags) {
}
