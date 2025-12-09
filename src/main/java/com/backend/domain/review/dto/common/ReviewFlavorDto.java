package com.backend.domain.review.dto.common;

import lombok.Builder;

@Builder
public record ReviewFlavorDto(
	Long reviewId,
	Long flavorId
) {
}
