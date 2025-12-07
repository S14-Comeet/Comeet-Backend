package com.backend.domain.visit.dto.common;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record VisitPageDto(
	Long visitId,
	Long menuId,
	Boolean verified,
	LocalDateTime visitedAt
) {
}
