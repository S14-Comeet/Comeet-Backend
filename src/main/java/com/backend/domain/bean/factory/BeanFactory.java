package com.backend.domain.bean.factory;

import org.springframework.stereotype.Component;

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
			.roasteryId(existingBean.getRoasteryId())
			.country(getOrDefault(dto.country(), existingBean.getCountry()))
			.farm(getOrDefault(dto.farm(), existingBean.getFarm()))
			.variety(getOrDefault(dto.variety(), existingBean.getVariety()))
			.processingMethod(getOrDefault(dto.processingMethod(), existingBean.getProcessingMethod()))
			.roastingLevel(getOrDefault(dto.roastingLevel(), existingBean.getRoastingLevel()))
			.createdAt(existingBean.getCreatedAt())
			.build();

		beanValidator.validate(bean);
		return bean;
	}

	private <T> T getOrDefault(final T newValue, final T existingValue) {
		return newValue != null ? newValue : existingValue;
	}
}
