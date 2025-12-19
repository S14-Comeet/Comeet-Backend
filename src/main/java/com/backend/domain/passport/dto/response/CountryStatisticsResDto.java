package com.backend.domain.passport.dto.response;

import java.util.List;

import com.backend.domain.passport.dto.common.CountryStatDto;

import lombok.Builder;

@Builder
public record CountryStatisticsResDto(
	List<CountryStatDto> countries
) {
}
