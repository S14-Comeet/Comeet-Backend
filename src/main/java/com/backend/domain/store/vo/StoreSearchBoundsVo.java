package com.backend.domain.store.vo;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;

@Builder
public record StoreSearchBoundsVo(
	BigDecimal minLatitude,
	BigDecimal maxLatitude,
	BigDecimal minLongitude,
	BigDecimal maxLongitude,
	List<String> categories,
	String keyword
) {
}
