package com.backend.domain.visit.dto.request;

import com.backend.domain.store.dto.common.StoreLocationDto;
import com.backend.domain.user.dto.common.UserLocationDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Schema(description = "방문 인증 요청 DTO")
public record VerifyReqDto(
	@Schema(description = "인증할 메뉴 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull(message = "메뉴 ID는 필수 입력값입니다.")
	Long menuId,

	@Schema(description = "가게 위치 정보", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull(message = "가게 위치 정보는 필수 입력값입니다.")
	@Valid
	StoreLocationDto storeLocationDto,

	@Schema(description = "사용자 현재 위치 정보", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull(message = "사용자 위치 정보는 필수 입력값입니다.")
	@Valid
	UserLocationDto userLocationDto
) {
}
