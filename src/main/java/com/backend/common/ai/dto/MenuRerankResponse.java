package com.backend.common.ai.dto;

import java.util.List;

/**
 * 메뉴 LLM 리랭킹 응답 DTO
 */
public record MenuRerankResponse(
	List<MenuRecommendationItem> recommendations
) {

	public record MenuRecommendationItem(
		Long menuId,
		Integer rank,
		String reason
	) {
	}
}
