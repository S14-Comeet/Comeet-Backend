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
}
