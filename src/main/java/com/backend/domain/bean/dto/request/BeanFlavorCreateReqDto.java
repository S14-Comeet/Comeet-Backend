package com.backend.domain.bean.dto.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotEmpty;

@Schema(description = "원두-플레이버 매핑 생성 요청 DTO")
public record BeanFlavorCreateReqDto(
	@Schema(description = "Flavor ID 리스트", example = "[1, 5, 10]", requiredMode = RequiredMode.REQUIRED)
	@NotEmpty(message = "Flavor ID 리스트는 필수 입력값입니다.")
	List<Long> flavorIds
) {
}
