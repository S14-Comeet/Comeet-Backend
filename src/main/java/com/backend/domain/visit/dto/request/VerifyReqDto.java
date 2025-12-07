package com.backend.domain.visit.dto.request;

import com.backend.domain.store.dto.common.StoreLocationDto;
import com.backend.domain.user.dto.common.UserLocationDto;

public record VerifyReqDto(
	Long menuId,
	StoreLocationDto storeLocationDto,
	UserLocationDto userLocationDto
) {
}
