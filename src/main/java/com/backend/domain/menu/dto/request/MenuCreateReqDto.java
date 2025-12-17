package com.backend.domain.menu.dto.request;

import com.backend.domain.menu.enums.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MenuCreateReqDto(
	@NotBlank(message = "메뉴 이름은 필수 입력값입니다.")
	String name,

	String description,

	@NotNull(message = "가격은 필수 입력값입니다.")
	@Positive(message = "가격은 0보다 커야 합니다.")
	Integer price,

	@NotNull(message = "카테고리는 필수 입력값입니다.")
	Category category,

	String imageUrl
) {
}
