package com.backend.domain.visit.dto.common;

import lombok.Builder;

@Builder
public record VisitPageDto (
	Long visitId
) {
}
