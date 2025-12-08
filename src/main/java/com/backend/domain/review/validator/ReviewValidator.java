package com.backend.domain.review.validator;

import org.springframework.stereotype.Component;

import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.ReviewException;
import com.backend.common.validator.Validator;
import com.backend.domain.review.entity.Review;

@Component
public class ReviewValidator implements Validator<Review> {
	@Override
	public void validate(final Review review) {
		validateNotNull(review);
	}

	private void validateNotNull(final Review review) {
		if (review == null) {
			throw new ReviewException(ErrorCode.INVALID_REVIEW_REQUEST);
		}
	}
}
