package com.backend.domain.roastery.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

@Schema(description = "로스터리 수정 요청 DTO")
public record RoasteryUpdateReqDto(
	@Schema(description = "로스터리 이름", example = "블루보틀 코리아", requiredMode = RequiredMode.NOT_REQUIRED)
	String name,

	@Schema(description = "로고 이미지 URL", example = "https://example.com/logo.png", requiredMode = RequiredMode.NOT_REQUIRED)
	String logoUrl,

	@Schema(description = "웹사이트 URL", example = "https://bluebottlecoffee.kr", requiredMode = RequiredMode.NOT_REQUIRED)
	String websiteUrl
) {
}
