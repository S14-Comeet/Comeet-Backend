package com.backend.common.ai.dto;

import java.util.List;

/**
 * LLM 리랭킹 응답 DTO
 */
public record RerankResponse(
	List<RecommendationItem> recommendations
) {

	public record RecommendationItem(
		Long beanId,
		Integer rank,
		String reason
	) {
	}
}
