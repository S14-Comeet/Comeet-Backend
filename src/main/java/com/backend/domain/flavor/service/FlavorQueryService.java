package com.backend.domain.flavor.service;

import java.util.List;

import com.backend.domain.flavor.entity.Flavor;

public interface FlavorQueryService {
	List<Flavor> findAllByIds(List<Long> flavorIdList);

	List<Flavor> findFlavorsByReviewId(final Long reviewId);

	List<Flavor> findAll();

}
