package com.backend.domain.ai.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public record BatchImageGenerationReqDto(
	@NotEmpty(message = "사용자 ID 목록은 필수입니다")
	List<Long> userIds
) {
}
