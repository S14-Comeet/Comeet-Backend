package com.backend.domain.review.service.command;

import com.backend.domain.review.entity.Review;

public interface ReviewCommandService {
	int insert(Review review);

	int update(Review review);

	int softDelete(Long reviewId);
}
