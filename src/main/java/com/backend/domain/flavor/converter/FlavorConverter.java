package com.backend.domain.flavor.converter;

import com.backend.domain.flavor.dto.common.FlavorBadgeDto;
import com.backend.domain.flavor.entity.Flavor;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FlavorConverter {

	public FlavorBadgeDto toFlavorBadgeDto(final Flavor flavor) {
		return FlavorBadgeDto.builder()
			.flavorId(flavor.getId())
			.code(flavor.getCode())
			.colorHex(flavor.getColorHex())
			.build();
	}
}
