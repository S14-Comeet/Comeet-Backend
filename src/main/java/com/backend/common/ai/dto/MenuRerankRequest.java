package com.backend.common.ai.dto;

import java.util.List;

/**
 * 메뉴 LLM 리랭킹 요청 DTO
 */
public record MenuRerankRequest(
	RerankRequest.UserPreferenceInfo userPreference,
	List<RerankRequest.MenuCandidateInfo> menuCandidates
) {
}
