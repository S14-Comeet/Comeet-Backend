package com.backend.domain.bean.converter;

import java.util.List;

import com.backend.domain.bean.dto.response.BeanResDto;
import com.backend.domain.bean.entity.Bean;
import com.backend.domain.flavor.converter.FlavorConverter;
import com.backend.domain.flavor.entity.Flavor;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BeanConverter {

	public BeanResDto toBeanResDto(final Bean bean, final List<Flavor> flavors) {
		return BeanResDto.builder()
			.id(bean.getId())
			.roasteryId(bean.getRoasteryId())
			.country(bean.getCountry())
			.farm(bean.getFarm())
			.variety(bean.getVariety())
			.processingMethod(bean.getProcessingMethod())
			.roastingLevel(bean.getRoastingLevel())
			.flavors(FlavorConverter.toFlavorBadgeDtoList(flavors))
			.createdAt(bean.getCreatedAt())
			.updatedAt(bean.getUpdatedAt())
			.build();
	}
}
