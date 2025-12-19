package com.backend.domain.passport.service.query.implement;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.PassportException;
import com.backend.domain.passport.entity.Passport;
import com.backend.domain.passport.mapper.query.PassportQueryMapper;
import com.backend.domain.passport.service.query.PassportQueryService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PassportQueryServiceImpl implements PassportQueryService {

	private final PassportQueryMapper passportQueryMapper;

	@Override
	public Passport findById(final Long passportId) {
		log.info("[Passport] 여권 조회 : passportId={}", passportId);
		Passport passport = passportQueryMapper.findById(passportId);
		if (passport == null) {
			throw new PassportException(ErrorCode.PASSPORT_NOT_FOUND);
		}
		return passport;
	}

	@Override
	public List<Passport> findAllByUserIdAndYear(final Long userId, final Integer year) {
		log.info("[Passport] 여권 목록 조회 : userId={}, year={}", userId, year);
		return passportQueryMapper.findAllByUserIdAndYear(userId, year);
	}
}
