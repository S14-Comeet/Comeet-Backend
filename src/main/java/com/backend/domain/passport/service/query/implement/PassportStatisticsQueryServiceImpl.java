package com.backend.domain.passport.service.query.implement;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.domain.passport.dto.common.CountryStatDto;
import com.backend.domain.passport.dto.common.RoasteryStatDto;
import com.backend.domain.passport.mapper.query.PassportStatisticsQueryMapper;
import com.backend.domain.passport.service.query.PassportStatisticsQueryService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PassportStatisticsQueryServiceImpl implements PassportStatisticsQueryService {

	private final PassportStatisticsQueryMapper passportStatisticsQueryMapper;

	@Override
	public List<RoasteryStatDto> getRoasteryStatistics(final Long userId) {
		log.info("[Passport] 로스터리 통계 조회 : userId={}", userId);
		return passportStatisticsQueryMapper.getRoasteryStatistics(userId);
	}

	@Override
	public List<CountryStatDto> getCountryStatistics(final Long userId) {
		log.info("[Passport] 국가 통계 조회 : userId={}", userId);
		return passportStatisticsQueryMapper.getCountryStatistics(userId);
	}
}
