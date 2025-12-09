package com.backend.domain.review.converter;

import java.util.List;

import com.backend.domain.review.dto.common.FlavorWheelBadgeDto;
import com.backend.domain.review.dto.common.ReviewInfoDto;
import com.backend.domain.review.dto.common.ReviewPageDto;
import com.backend.domain.review.dto.response.ReviewedResDto;
import com.backend.domain.review.entity.Review;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ReviewConverter {

	public static ReviewedResDto toReviewedResDto(final Review review, final List<FlavorWheelBadgeDto> badgeDtos) {
		return ReviewedResDto.builder()
			.reviewInfoDto(toReviewInfoDto(review))
			.flavorWheelBadgeDto(badgeDtos)
			.build();
	}

	public static ReviewPageDto toReviewPageDto(final Review review, final List<FlavorWheelBadgeDto> badgeDtos) {
		return ReviewPageDto.builder()
			.reviewId(review.getId())
			.visitId(review.getVisitId())
			.menuId(review.getMenuId())
			.content(review.getContent())
			.imageUrl(review.getImageUrl())
			.isPublic(review.getIsPublic())
			.flavorWheelBadges(badgeDtos)
			.createdAt(review.getCreatedAt())
			.build();
	}

	private static ReviewInfoDto toReviewInfoDto(final Review review) {
		return ReviewInfoDto.builder()
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