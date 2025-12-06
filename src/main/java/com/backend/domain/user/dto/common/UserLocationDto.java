package com.backend.domain.user.dto.common;

import java.math.BigDecimal;

public record UserLocationDto(
	BigDecimal latitude,
	BigDecimal longitude
) {
}
