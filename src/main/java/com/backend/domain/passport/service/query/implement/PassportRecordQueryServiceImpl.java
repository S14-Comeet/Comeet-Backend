package com.backend.domain.passport.service.query.implement;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.domain.passport.dto.common.PassportRecordDto;
import com.backend.domain.passport.mapper.query.PassportRecordQueryMapper;
import com.backend.domain.passport.service.query.PassportRecordQueryService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PassportRecordQueryServiceImpl implements PassportRecordQueryService {

	private final PassportRecordQueryMapper passportRecordQueryMapper;

	@Override
	public List<PassportRecordDto> findRecordsByPassportId(final Long passportId) {
		log.info("[Passport] 여권 기록 조회 : passportId={}", passportId);
		return passportRecordQueryMapper.findRecordsByPassportId(passportId);
	}
}
