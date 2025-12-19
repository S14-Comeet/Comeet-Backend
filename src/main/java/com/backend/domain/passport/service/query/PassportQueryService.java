package com.backend.domain.passport.service.query;

import java.util.List;

import com.backend.domain.passport.entity.Passport;

public interface PassportQueryService {

	Passport findById(Long passportId);

	List<Passport> findAllByUserIdAndYear(Long userId, Integer year);
}
