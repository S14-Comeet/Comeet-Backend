package com.backend.domain.passport.service.query;

import java.util.List;

import com.backend.domain.passport.dto.common.CountryStatDto;
import com.backend.domain.passport.dto.common.RoasteryStatDto;

public interface PassportStatisticsQueryService {

	List<RoasteryStatDto> getRoasteryStatistics(Long userId);

	List<CountryStatDto> getCountryStatistics(Long userId);
}
