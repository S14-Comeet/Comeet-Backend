package com.backend.domain.passport.dto.response;

import java.util.List;

import com.backend.domain.passport.dto.common.RoasteryStatDto;

import lombok.Builder;

@Builder
public record RoasteryStatisticsResDto(
	List<RoasteryStatDto> roasteries
) {
}
