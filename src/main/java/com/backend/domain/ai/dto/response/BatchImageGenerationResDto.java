package com.backend.domain.ai.dto.response;

import lombok.Builder;

@Builder
public record BatchImageGenerationResDto(
	String batchId,
	int totalTasks,
	String message
) {
}
