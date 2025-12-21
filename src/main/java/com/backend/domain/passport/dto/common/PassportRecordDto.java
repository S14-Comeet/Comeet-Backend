package com.backend.domain.passport.dto.common;

import java.time.LocalDateTime;

public record PassportRecordDto(
	Long visitId,
	LocalDateTime visitDate,
	Long storeId,
	String storeName,
	String storeAddress,
	double latitude,
	double longitude,
	String beanOrigin,
	String menuName
) {
}
