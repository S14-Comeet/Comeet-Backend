package com.backend.domain.review.converter;

import com.backend.domain.review.dto.common.FlavorWheelBadgeDto;
import com.backend.domain.review.entity.FlavorWheel;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FlavorWheelConverter {

	public FlavorWheelBadgeDto toFlavorWheelBadgeDto(final FlavorWheel flavorWheel) {
		return FlavorWheelBadgeDto.builder()
			.id(flavorWheel.getId())
			.code(flavorWheel.getCode())
			.colorHex(flavorWheel.getColorHex())
			.build();
	}
}
