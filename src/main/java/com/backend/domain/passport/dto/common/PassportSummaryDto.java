package com.backend.domain.passport.dto.common;

import lombok.Builder;

@Builder
public record PassportSummaryDto(
	Long passportId,
	Integer month,
	String coverImageUrl,
	Integer totalCoffeeCount,
	Integer totalStoreCount,
	String topOrigin,
	Boolean isAvailable
) {
}
