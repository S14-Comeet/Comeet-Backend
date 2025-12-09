package com.backend.domain.flavor.converter;

import com.backend.domain.flavor.dto.common.FlavorWheelBadgeDto;
import com.backend.domain.flavor.entity.FlavorWheel;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FlavorWheelConverter {

	public FlavorWheelBadgeDto toFlavorWheelBadgeDto(final FlavorWheel flavorWheel) {
		return FlavorWheelBadgeDto.builder()
			.flavorWheelId(flavorWheel.getId())
			.code(flavorWheel.getCode())
			.colorHex(flavorWheel.getColorHex())
			.build();
	}
}
