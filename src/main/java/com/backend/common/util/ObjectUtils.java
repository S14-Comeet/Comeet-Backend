package com.backend.common.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ObjectUtils {

	/**
	 * 새로운 값이 null이 아니면 새로운 값을 반환하고, null이면 기존 값을 반환합니다.
	 * 주로 엔티티 업데이트 시 부분 수정을 처리할 때 사용됩니다.
	 */
	public <T> T getOrDefault(final T newValue, final T existingValue) {
		return newValue != null ? newValue : existingValue;
	}
}
