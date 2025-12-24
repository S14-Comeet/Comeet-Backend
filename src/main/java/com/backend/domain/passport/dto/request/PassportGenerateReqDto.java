package com.backend.domain.passport.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "여권 생성 테스트 요청 DTO")
public record PassportGenerateReqDto(
	@Schema(description = "생성할 년도", example = "2024", requiredMode = RequiredMode.REQUIRED)
	@NotNull(message = "년도는 필수 입력값입니다.")
	@Min(value = 2020, message = "년도는 2020 이상이어야 합니다.")
	@Max(value = 2100, message = "년도는 2100 이하여야 합니다.")
	Integer year,

	@Schema(description = "생성할 월", example = "12", requiredMode = RequiredMode.REQUIRED)
	@NotNull(message = "월은 필수 입력값입니다.")
	@Min(value = 1, message = "월은 1 이상이어야 합니다.")
	@Max(value = 12, message = "월은 12 이하여야 합니다.")
	Integer month,

	@Schema(description = "특정 사용자 ID (없으면 해당 월에 방문한 모든 사용자)", example = "1", requiredMode = RequiredMode.NOT_REQUIRED)
	Long userId
) {
}
