package com.backend.domain.passport.dto.common;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;

@Builder
public record PassportInfoDto(
	Integer totalCoffeeCount,
	Integer totalStoreCount,
	Integer totalBeanCount,
	String topOrigin,
	String topRoastery,
	List<String> originSequence,
	BigDecimal totalOriginDistance
) {
}
