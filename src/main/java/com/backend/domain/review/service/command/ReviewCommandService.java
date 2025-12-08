package com.backend.domain.review.service.command;

import com.backend.domain.review.entity.Review;

public interface ReviewCommandService {
	int insert(Review newReview);

	int update(Review review);
}
