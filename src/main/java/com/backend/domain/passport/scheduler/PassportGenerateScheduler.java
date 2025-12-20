package com.backend.domain.passport.scheduler;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.PassportException;
import com.backend.domain.passport.converter.PassportConverter;
import com.backend.domain.passport.entity.Passport;
import com.backend.domain.passport.mapper.query.PassportQueryMapper;
import com.backend.domain.passport.service.calculator.PassportStatisticsCalculator;
import com.backend.domain.passport.service.calculator.PassportStatisticsCalculator.PassportStatistics;
import com.backend.domain.passport.service.command.PassportCommandService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PassportGenerateScheduler {

	private final PassportCommandService passportCommandService;
	private final PassportQueryMapper passportQueryMapper;
	private final PassportStatisticsCalculator statisticsCalculator;

	//? 비동기 작업으로 작업을 효율적으로 처리하는 것이 가능할 것 같으니, 추후 검토가 필요
	@Scheduled(cron = "0 0 0 1 * ?")
	public void generateMonthlyPassports() {
		LocalDate now = LocalDate.now();
		YearMonth previousMonth = YearMonth.from(now.minusMonths(1));
		int year = previousMonth.getYear();
		int month = previousMonth.getMonthValue();

		log.info("[Passport] 월간 여권 생성 시작, year={}, month={}", year, month);

		try {
			List<Long> userIds = passportQueryMapper.findUsersWithVisitsInMonth(year, month);
			log.info("[Passport] 대상 사용자 조회 완료, count={}", userIds.size());

			int successCount = 0;
			int failCount = 0;

			for (Long userId : userIds) {
				try {
					generatePassportForUser(userId, year, month);
					successCount++;
				} catch (Exception e) {
					log.error("[Passport] 여권 생성 실패, userId={}", userId, e);
					failCount++;
				}
			}

			log.info("[Passport] 월간 여권 생성 완료, success={}, failed={}", successCount, failCount);

		} catch (Exception e) {
			log.error("[Passport] 월간 여권 생성 중 오류 발생", e);
			throw new PassportException(ErrorCode.PASSPORT_GENERATION_FAILED);
		}
	}

	private void generatePassportForUser(Long userId, int year, int month) {
		Optional<Passport> existingPassport = passportQueryMapper.findByUserIdAndYearAndMonth(userId, year, month);
		if (existingPassport.isPresent()) {
			log.warn("[Passport] 여권 이미 존재, userId={}, year={}, month={}", userId, year, month);
			return;
		}

		List<Map<String, Object>> visits = passportQueryMapper.findVisitsWithMenuInMonth(userId, year, month);
		if (CollectionUtils.isEmpty(visits)) {
			return;
		}

		PassportStatistics stats = statisticsCalculator.calculate(visits);

		Passport passport = PassportConverter.toPassport(userId, year, month, stats);
		Long passportId = passportCommandService.createPassport(passport);

		for (Map<String, Object> visit : visits) {
			Long visitId = (Long)visit.get("visit_id");
			passportCommandService.addPassportVisit(passportId, visitId);
		}

		log.info("[Passport] 여권 생성 완료, passportId={}, userId={}", passportId, userId);
	}
}
