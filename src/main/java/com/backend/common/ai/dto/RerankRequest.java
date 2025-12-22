package com.backend.common.ai.dto;

import java.util.List;

/**
 * LLM 리랭킹 요청 DTO
 */
public record RerankRequest(
	UserPreferenceInfo userPreference,
	List<CandidateInfo> candidates
) {

	public record UserPreferenceInfo(
		Integer prefAcidity,
		Integer prefBody,
		Integer prefSweetness,
		Integer prefBitterness,
		List<String> preferredRoastLevels,
		List<String> likedTags
	) {
	}

	/**
	 * 원두 추천용 후보 정보
	 */
	public record CandidateInfo(
		Long beanId,
		String beanName,
		String roasteryName,
		String country,
		String roastLevel,
		List<String> flavorTags,
		Integer acidity,
		Integer body,
		Integer sweetness,
		Integer bitterness,
		Double similarityScore
	) {
	}

	/**
	 * 메뉴 추천용 후보 정보 (메뉴 + 원두 정보 포함)
	 */
	public record MenuCandidateInfo(
		// 메뉴 정보
		Long menuId,
		String menuName,
		String menuDescription,
		// 원두 정보
		Long beanId,
		String beanName,
		String roasteryName,
		String beanCountry,
		String roastLevel,
		List<String> flavorTags,
		Integer acidity,
		Integer body,
		Integer sweetness,
		Integer bitterness,
		Double similarityScore
	) {
	}
}
