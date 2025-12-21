package com.backend.domain.preference.dto.response;

import java.util.List;

import com.backend.domain.bean.enums.RoastingLevel;

/**
 * 사용자 취향 응답 DTO
 */
public record PreferenceResDto(
	Long id,
	Long userId,
	Integer prefAcidity,
	Integer prefBody,
	Integer prefSweetness,
	Integer prefBitterness,
	List<RoastingLevel> preferredRoastLevels,
	List<String> likedTags,
	List<String> dislikedTags
) {
}
