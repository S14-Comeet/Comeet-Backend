package com.backend.domain.flavor.service;

import java.util.List;

import com.backend.domain.flavor.entity.Flavor;

public interface FlavorQueryService {
	List<Flavor> findAllByIds(List<Long> flavorIdList);

	List<Flavor> findFlavorsByReviewId(final Long reviewId);

	List<Flavor> findAll();

	List<Flavor> findByCodes(List<String> codes);

	/**
	 * 플레이버 코드 리스트를 계층 구조 경로 리스트로 변환
	 * 예: ["ROSE", "BLACKBERRY"] → ["FLORAL > FLORAL_SUB > ROSE", "FRUITY > BERRY > BLACKBERRY"]
	 *
	 * @param codes 플레이버 코드 리스트
	 * @return 계층 구조 경로 리스트
	 */
	List<String> getHierarchyPaths(List<String> codes);
}
