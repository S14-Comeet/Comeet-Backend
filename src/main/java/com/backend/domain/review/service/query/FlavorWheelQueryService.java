package com.backend.domain.review.service.query;

import java.util.List;

import com.backend.domain.review.entity.FlavorWheel;

public interface FlavorWheelQueryService {
	List<FlavorWheel> findAllByIds(List<Long> flavorWheelIdList);

	List<FlavorWheel> findFlavorWheelsByReviewId(final Long reviewId);
}
