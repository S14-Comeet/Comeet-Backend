package com.backend.domain.flavor.converter;

import java.util.List;

import com.backend.domain.flavor.dto.common.FlavorBadgeDto;
import com.backend.domain.flavor.dto.common.FlavorInfoDto;
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

	public FlavorInfoDto toFlavorInfoDto(final Flavor flavor) {
		return FlavorInfoDto.builder()
			.id(flavor.getId())
			.code(flavor.getCode())
			.parentId(flavor.getParentId())
			.level(flavor.getLevel())
			.path(flavor.getPath())
			.name(flavor.getName())
			.description(flavor.getDescription())
			.colorHex(flavor.getColorHex())
			.build();
	}

	public List<FlavorBadgeDto> toFlavorBadgeDtoList(final List<Flavor> flavors) {
		if (flavors == null) {
			return List.of();
		}
		return flavors.stream()
			.map(FlavorConverter::toFlavorBadgeDto)
			.toList();
	}
}
