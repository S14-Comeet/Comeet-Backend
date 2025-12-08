package com.backend.domain.review.dto.common;

import lombok.Builder;

@Builder
public record FlavorWheelBadgeDto(
	Long id,
	String code,
	String colorHex
) {
}
