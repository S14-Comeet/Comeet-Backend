package com.backend.domain.visit.dto.request;

import java.math.BigDecimal;

import com.backend.domain.user.dto.common.UserLocationDto;

public record VerifyReqDto(
	BigDecimal storeLatitude,
	BigDecimal storeLongitude,
	UserLocationDto locationDto
) {
}
