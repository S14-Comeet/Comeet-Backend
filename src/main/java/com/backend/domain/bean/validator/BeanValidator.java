package com.backend.domain.bean.validator;

import org.springframework.stereotype.Component;

import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.BeanException;
import com.backend.common.validator.Validator;
import com.backend.domain.bean.entity.Bean;

@Component
public class BeanValidator implements Validator<Bean> {
	@Override
	public void validate(final Bean bean) {
		validateNotNull(bean);
		validateCountry(bean.getCountry());
	}

	private void validateNotNull(final Bean bean) {
		if (bean == null) {
			throw new BeanException(ErrorCode.INVALID_BEAN_REQUEST);
		}
	}

	private void validateCountry(final String country) {
		if (country == null || country.isBlank()) {
			throw new BeanException(ErrorCode.BEAN_COUNTRY_REQUIRED);
		}
	}

	public void validateBelongsToRoastery(final Bean bean, final Long roasteryId) {
		if (!bean.getRoasteryId().equals(roasteryId)) {
			throw new BeanException(ErrorCode.BEAN_ACCESS_DENIED);
		}
	}
}
