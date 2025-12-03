package com.backend.common.util;

import java.util.List;
import java.util.stream.Stream;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtils {

	/**
	 * 카테고리 문자열을 리스트로 변환
	 * @param categories 콤마로 구분된 카테고리 문자열
	 * @return 카테고리 리스트
	 */
	public List<String> parseCategoryList(final String categories) {
		if (categories == null || categories.trim().isEmpty()) {
			return List.of();
		}
		return Stream.of(categories.split(","))
			.map(String::trim)
			.filter(s -> !s.isEmpty())
			.toList();
	}
}
