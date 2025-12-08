package com.backend.domain.review.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record ReviewedResDto(
	Long reviewId,
	Long userId,
	Long menuId,
	Long visitId,
	String content,
	String imageUrl,
	Boolean isPublic,
	LocalDateTime createdAt
) {
}
