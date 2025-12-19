package com.backend.domain.passport.dto.common;

import lombok.Builder;

@Builder
public record RoasteryStatDto(
	String roasteryName,
	Integer visitCount
) {
}
