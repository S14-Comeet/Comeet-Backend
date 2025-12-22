package com.backend.domain.bean.converter;

import java.util.List;

import com.backend.domain.bean.dto.common.BeanBadgeDto;
import com.backend.domain.bean.dto.response.BeanResDto;
import com.backend.domain.bean.entity.Bean;
import com.backend.domain.flavor.dto.common.FlavorBadgeDto;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BeanConverter {

	public BeanResDto toBeanResDto(final Bean bean, final List<FlavorBadgeDto> flavors) {
		return BeanResDto.builder()
			.id(bean.getId())
			.roasteryId(bean.getRoasteryId())
			.name(bean.getName())
			.country(bean.getCountry())
			.farm(bean.getFarm())
			.variety(bean.getVariety())
			.processingMethod(bean.getProcessingMethod())
			.roastingLevel(bean.getRoastingLevel())
			.flavors(flavors)
			.createdAt(bean.getCreatedAt())
			.updatedAt(bean.getUpdatedAt())
			.build();
	}

	public BeanBadgeDto toBeanBadgeDto(final Bean bean) {
		return BeanBadgeDto.builder()
			.id(bean.getId())
			.name(bean.getName())
			.build();
	}

	public List<BeanBadgeDto> toBeanBadgeDtos(final List<Bean> beans) {
		return beans.stream()
			.map(BeanConverter::toBeanBadgeDto)
			.toList();
	}

}
