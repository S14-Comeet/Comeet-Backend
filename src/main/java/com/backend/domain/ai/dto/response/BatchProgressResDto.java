package com.backend.domain.ai.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;

/**
 * 배치 진행 상황 응답 DTO
 */
@Builder
public record BatchProgressResDto(
	String batchId,
	int total,
	int completed,
	int failed,
	int remaining,
	double progressPercentage,
	boolean isCompleted,
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime startTime,
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime endTime,
	Long elapsedSeconds,
	String statusMessage
) {
}
