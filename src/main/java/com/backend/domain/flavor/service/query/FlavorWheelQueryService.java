package com.backend.domain.flavor.service.query;

import java.util.List;

import com.backend.domain.flavor.entity.FlavorWheel;

public interface FlavorWheelQueryService {
	List<FlavorWheel> findAllByIds(List<Long> flavorWheelIdList);

	List<FlavorWheel> findFlavorWheelsByReviewId(final Long reviewId);
}
