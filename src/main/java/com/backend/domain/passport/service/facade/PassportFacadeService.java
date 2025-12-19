package com.backend.domain.passport.service.facade;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.domain.passport.converter.PassportConverter;
import com.backend.domain.passport.converter.StatisticsConverter;
import com.backend.domain.passport.dto.common.CountryStatDto;
import com.backend.domain.passport.dto.common.PassportRecordDto;
import com.backend.domain.passport.dto.common.PassportSummaryDto;
import com.backend.domain.passport.dto.common.RoasteryStatDto;
import com.backend.domain.passport.dto.response.CountryStatisticsResDto;
import com.backend.domain.passport.dto.response.PassportDetailResDto;
import com.backend.domain.passport.dto.response.PassportListResDto;
import com.backend.domain.passport.dto.response.RoasteryStatisticsResDto;
import com.backend.domain.passport.entity.Passport;
import com.backend.domain.passport.service.query.PassportQueryService;
import com.backend.domain.passport.service.query.PassportRecordQueryService;
import com.backend.domain.passport.service.query.PassportStatisticsQueryService;
import com.backend.domain.passport.validator.PassportValidator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PassportFacadeService {

	private final PassportQueryService passportQueryService;
	private final PassportRecordQueryService passportRecordQueryService;
	private final PassportStatisticsQueryService passportStatisticsQueryService;
	private final PassportValidator passportValidator;

	public PassportListResDto getPassportList(final Long userId, final Integer year) {
		passportValidator.validateYear(year);
		List<Passport> passports = passportQueryService.findAllByUserIdAndYear(userId, year);

		List<PassportSummaryDto> summaries = passports.stream()
			.map(passport -> {
				boolean isAvailable = passportValidator.isAvailable(passport.getYear(), passport.getMonth());
				return PassportConverter.toPassportSummaryDto(passport, isAvailable);
			})
			.toList();

		return PassportConverter.toPassportListResDto(year, summaries);
	}

	public PassportDetailResDto getPassportDetail(final Long passportId, final Long userId) {
		Passport passport = passportQueryService.findById(passportId);
		passportValidator.validateOwnership(passport, userId);
		passportValidator.validateAvailability(passport);

		List<PassportRecordDto> records = passportRecordQueryService.findRecordsByPassportId(passportId);
		return PassportConverter.toPassportDetailResDto(passport, records);
	}

	public RoasteryStatisticsResDto getRoasteryStatistics(final Long userId) {
		List<RoasteryStatDto> statistics = passportStatisticsQueryService.getRoasteryStatistics(userId);
		return StatisticsConverter.toRoasteryStatDto(statistics);
	}

	public CountryStatisticsResDto getCountryStatistics(final Long userId) {
		List<CountryStatDto> statistics = passportStatisticsQueryService.getCountryStatistics(userId);
		return StatisticsConverter.toCountryStatisticsResDto(statistics);
	}
}
