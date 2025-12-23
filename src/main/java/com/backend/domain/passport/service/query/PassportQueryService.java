package com.backend.domain.passport.service.query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.backend.domain.passport.entity.Passport;

public interface PassportQueryService {

	Passport findById(Long passportId);

	List<Passport> findAllByUserIdAndYear(Long userId, Integer year);

	List<Long> findUsersWithVisitsInMonth(int year, int month);

	Optional<Passport> findByUserIdAndYearAndMonth(Long userId, int year, int month);

	List<Map<String, Object>> findVisitsWithMenuInMonth(Long userId, int year, int month);

	Passport findLatestByUserId(Long userId);
}
