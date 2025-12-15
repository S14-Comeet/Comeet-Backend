package com.backend.domain.roastery.converter;

import com.backend.domain.roastery.dto.response.RoasteryResDto;
import com.backend.domain.roastery.entity.Roastery;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RoasteryConverter {

	public static RoasteryResDto toRoasteryResDto(final Roastery roastery) {
		return RoasteryResDto.builder()
			.id(roastery.getId())
			.ownerId(roastery.getOwnerId())
			.name(roastery.getName())
			.logoUrl(roastery.getLogoUrl())
			.websiteUrl(roastery.getWebsiteUrl())
			.createdAt(roastery.getCreatedAt())
			.updatedAt(roastery.getUpdatedAt())
			.build();
	}
}
