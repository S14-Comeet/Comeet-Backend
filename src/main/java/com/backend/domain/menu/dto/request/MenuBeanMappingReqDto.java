package com.backend.domain.menu.dto.request;

import jakarta.validation.constraints.NotNull;

public record MenuBeanMappingReqDto(
	@NotNull(message = "원두 ID는 필수 입력값입니다.")
	Long beanId,

	Boolean isBlended
) {
}
