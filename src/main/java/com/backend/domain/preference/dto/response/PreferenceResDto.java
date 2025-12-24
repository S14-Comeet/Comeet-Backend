package com.backend.domain.preference.dto.response;

import java.util.List;

import com.backend.domain.bean.enums.RoastingLevel;

public record PreferenceResDto(
		Long id,
		Long userId,
		Integer prefAcidity,
		Integer prefBody,
		Integer prefSweetness,
		Integer prefBitterness,
		List<RoastingLevel> preferredRoastLevels,
		List<String> likedTags,
		List<String> dislikedTags) {
}
