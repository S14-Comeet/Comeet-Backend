package com.backend.domain.review.factory;

import org.springframework.stereotype.Component;

import com.backend.domain.review.dto.request.ReviewReqDto;
import com.backend.domain.review.entity.Review;
import com.backend.domain.review.validator.ReviewValidator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewFactory {
	private final ReviewValidator reviewValidator;

	public Review create(final Long userId, final ReviewReqDto dto) {
		Review review = Review.builder()
			.userId(userId)
			.menuId(dto.menuId())
			.storeId(dto.storeId())
			.visitId(dto.visitId())
			.content(dto.content())
			.isPublic(dto.isPublic())
			.imageUrl(dto.imageUrl())
			.build();

		reviewValidator.validate(review);
		return review;
	}

	public Review createForUpdate(final Review existingReview, final ReviewReqDto dto) {
		Review review = Review.builder()
			.id(existingReview.getId())
			.userId(existingReview.getUserId())
			.visitId(existingReview.getVisitId())
			.menuId(existingReview.getMenuId())
			.storeId(existingReview.getStoreId())
			.content(dto.content())
			.isPublic(dto.isPublic())
			.imageUrl(dto.imageUrl())
			.build();

		reviewValidator.validate(review);
		return review;

	}
}
