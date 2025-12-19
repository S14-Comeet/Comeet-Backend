package com.backend.domain.image.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ImageResDto(
	@Schema(name = "이미지 경로")
	String url
) {
}
