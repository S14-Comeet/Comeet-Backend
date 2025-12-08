package com.backend.domain.review.dto.common;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record ReviewInfoDto (
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