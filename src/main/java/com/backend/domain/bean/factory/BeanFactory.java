package com.backend.domain.bean.factory;

import org.springframework.stereotype.Component;

import com.backend.common.util.ObjectUtils;
import com.backend.domain.bean.dto.request.BeanCreateReqDto;
import com.backend.domain.bean.dto.request.BeanUpdateReqDto;
import com.backend.domain.bean.entity.Bean;
import com.backend.domain.bean.validator.BeanValidator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class BeanFactory {
	private final BeanValidator beanValidator;

	public Bean create(final BeanCreateReqDto dto) {
		Bean bean = Bean.builder()
			.name(dto.name())
			.roasteryId(dto.roasteryId())
			.country(dto.country())
			.farm(dto.farm())
			.variety(dto.variety())
			.processingMethod(dto.processingMethod())
			.roastingLevel(dto.roastingLevel())
			.build();

		beanValidator.validate(bean);
		return bean;
	}

	public Bean createForUpdate(final Bean existingBean, final BeanUpdateReqDto dto) {
		Bean bean = Bean.builder()
			.id(existingBean.getId())
			.name(ObjectUtils.getOrDefault(dto.name(), existingBean.getName()))
			.roasteryId(existingBean.getRoasteryId())
			.country(ObjectUtils.getOrDefault(dto.country(), existingBean.getCountry()))
			.farm(ObjectUtils.getOrDefault(dto.farm(), existingBean.getFarm()))
			.variety(ObjectUtils.getOrDefault(dto.variety(), existingBean.getVariety()))
			.processingMethod(ObjectUtils.getOrDefault(dto.processingMethod(), existingBean.getProcessingMethod()))
			.roastingLevel(ObjectUtils.getOrDefault(dto.roastingLevel(), existingBean.getRoastingLevel()))
			.createdAt(existingBean.getCreatedAt())
			.build();

		beanValidator.validate(bean);
		return bean;
	}

}
