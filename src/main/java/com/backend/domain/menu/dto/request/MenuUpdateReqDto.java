package com.backend.domain.menu.dto.request;

import com.backend.domain.menu.enums.Category;

import jakarta.validation.constraints.Positive;

public record MenuUpdateReqDto(
	String name,
	String description,

	@Positive(message = "가격은 0보다 커야 합니다.")
	Integer price,

	Category category,
	String imageUrl
) {
}
