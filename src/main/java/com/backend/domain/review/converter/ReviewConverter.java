package com.backend.domain.review.converter;

import com.backend.domain.review.dto.response.ReviewedResDto;
import com.backend.domain.review.entity.Review;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ReviewConverter {
	public static ReviewedResDto toReviewedResDto(final Review review) {
		return ReviewedResDto.builder()
			.reviewId(review.getId())
			.userId(review.getUserId())
						.menuId(review.getMenuId())
			.visitId(review.getVisitId())
			.content(review.getContent())
			.imageUrl(review.getImageUrl())
			.isPublic(review.getIsPublic())
			.createdAt(review.getCreatedAt())
			.build();
	}
}
