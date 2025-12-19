package com.backend.domain.passport.dto.response;

import java.util.List;

import com.backend.domain.passport.dto.common.PassportSummaryDto;

import lombok.Builder;

@Builder
public record PassportListResDto(
	Integer year,
	List<PassportSummaryDto> passports
) {
}
