package com.backend.domain.review.service.query;

import com.backend.domain.review.entity.CuppingNote;

public interface CuppingNoteQueryService {
	CuppingNote findByReviewId(Long reviewId);

	boolean existsByReviewId(Long reviewId);
}

