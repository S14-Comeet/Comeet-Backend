package com.backend.domain.passport.converter;

import java.util.List;

import com.backend.domain.passport.dto.common.CountryStatDto;
import com.backend.domain.passport.dto.common.RoasteryStatDto;
import com.backend.domain.passport.dto.response.CountryStatisticsResDto;
import com.backend.domain.passport.dto.response.RoasteryStatisticsResDto;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StatisticsConverter {

	public RoasteryStatisticsResDto toRoasteryStatDto(final List<RoasteryStatDto> statistics) {
		return RoasteryStatisticsResDto.builder()
			.roasteries(statistics)
			.build();
	}

	public CountryStatisticsResDto toCountryStatisticsResDto(final List<CountryStatDto> statistics) {
		return CountryStatisticsResDto.builder()
			.countries(statistics)
			.build();
	}
}
