package com.backend.domain.review.dto.common;

import lombok.Builder;

@Builder
public record ReviewFlavorWheelDto(
	Long reviewId,
	Long flavorWheelId
) {
}
