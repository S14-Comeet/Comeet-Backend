package com.backend.common.util;

import java.util.List;
import java.util.function.Function;
import java.util.function.IntSupplier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PageUtils {

	public Pageable getPageable(final int page, final int size) {
		return PageRequest.of(page - 1, size);
	}

	public <T> Page<T> toPage(final List<T> content, final Pageable pageable, final long total) {
		return new PageImpl<>(content, pageable, total);
	}

	public <E, D> Page<D> buildPageResponse(
		final int page,
		final int size,
		final Function<Pageable, List<E>> finder,
		final IntSupplier counter,
		final Function<E, D> converter
	) {
		Pageable pageable = getPageable(page, size);
		List<E> entities = finder.apply(pageable);

		if (entities.isEmpty()) {
			return toPage(List.of(), pageable, 0);
		}

		int total = counter.getAsInt();
		List<D> dtoList = entities.stream()
			.map(converter)
			.toList();

		return toPage(dtoList, pageable, total);
	}
}
