package com.backend.domain.preference.converter;

import com.backend.domain.preference.dto.response.PreferenceResDto;
import com.backend.domain.preference.entity.UserPreference;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PreferenceConverter {

	public static PreferenceResDto toResDto(UserPreference entity) {
		return new PreferenceResDto(
				entity.getId(),
				entity.getUserId(),
				entity.getPrefAcidity(),
				entity.getPrefBody(),
				entity.getPrefSweetness(),
				entity.getPrefBitterness(),
				entity.getPreferredRoastLevels(),
				entity.getLikedTags(),
				entity.getDislikedTags());
	}
}
