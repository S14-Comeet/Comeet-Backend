package com.backend.domain.passport.service.command.implement;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.domain.passport.entity.Passport;
import com.backend.domain.passport.mapper.command.PassportCommandMapper;
import com.backend.domain.passport.service.command.PassportCommandService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PassportCommandServiceImpl implements PassportCommandService {

	private final PassportCommandMapper passportCommandMapper;

	@Override
	public Long createPassport(Passport passport) {
		passportCommandMapper.insertPassport(passport);
		return passport.getId();
	}

	@Override
	public void addPassportVisit(Long passportId, Long visitId) {
		passportCommandMapper.insertPassportVisit(passportId, visitId);
	}

	@Override
	public void updateCoverImage(Long passportId, String imageUrl) {
		passportCommandMapper.updateCoverImage(passportId, imageUrl);
		log.info("[Passport] 커버 이미지 업데이트 완료 - passportId: {}, imageUrl: {}", passportId, imageUrl);
	}
}
