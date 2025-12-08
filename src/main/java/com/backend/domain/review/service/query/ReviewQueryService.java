package com.backend.domain.review.service.query;

import com.backend.domain.review.entity.Review;

public interface ReviewQueryService {
	Review findById(Long reviewId);
}
