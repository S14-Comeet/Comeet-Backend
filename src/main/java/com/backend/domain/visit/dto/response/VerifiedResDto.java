package com.backend.domain.visit.dto.response;

import lombok.Builder;

@Builder
public record VerifiedResDto(
	Long visitId,
	Boolean isVerified
) {
}
