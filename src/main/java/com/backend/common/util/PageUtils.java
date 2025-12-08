package com.backend.common.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PageUtils {

	public Pageable getPageable(final int page, final int size) {
		return PageRequest.of(page - 1, size);
	}
}
