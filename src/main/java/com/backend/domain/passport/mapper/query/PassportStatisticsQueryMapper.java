package com.backend.domain.passport.mapper.query;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.backend.domain.passport.dto.common.CountryStatDto;
import com.backend.domain.passport.dto.common.RoasteryStatDto;

@Mapper
public interface PassportStatisticsQueryMapper {

	List<RoasteryStatDto> getRoasteryStatistics(@Param("userId") Long userId);

	List<CountryStatDto> getCountryStatistics(@Param("userId") Long userId);
}
