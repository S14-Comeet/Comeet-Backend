package com.backend.domain.store.dto.common;

import java.math.BigDecimal;

public record StoreLocationDto(
	BigDecimal latitude,
	BigDecimal longitude
) {
}
