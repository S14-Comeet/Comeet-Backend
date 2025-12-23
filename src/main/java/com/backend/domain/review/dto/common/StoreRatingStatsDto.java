package com.backend.domain.review.dto.common;

import java.math.BigDecimal;

public record StoreRatingStatsDto(
	BigDecimal averageRating,
	Integer reviewCount
) {
}
