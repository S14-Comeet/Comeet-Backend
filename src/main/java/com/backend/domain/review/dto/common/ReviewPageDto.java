package com.backend.domain.review.dto.common;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;

@Builder
public record ReviewPageDto(
	Long reviewId,
	Long visitId,
	Long menuId,
	String content,
	String imageUrl,
	Boolean isPublic,
	List<FlavorWheelBadgeDto> flavorWheelBadges,
	LocalDateTime createdAt
) {
}
