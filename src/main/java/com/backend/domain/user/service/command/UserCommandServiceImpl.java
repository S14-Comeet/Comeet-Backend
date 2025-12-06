package com.backend.domain.user.service.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.UserException;
import com.backend.domain.user.dto.request.UserRegisterReqDto;
import com.backend.domain.user.entity.User;
import com.backend.domain.user.mapper.command.UserCommandMapper;
import com.backend.domain.user.service.query.UserQueryService;
import com.backend.domain.user.validator.UserValidator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCommandServiceImpl implements UserCommandService {

	private final UserCommandMapper commandMapper;
	private final UserQueryService queryService;
	private final UserValidator userValidator;

	@Override
	public User save(final User user) {
		int saved = commandMapper.save(user);
		log.info("[User] 유저생성 완료 - 추가된 항목 {} 개 userId : {}", saved, user.getId());
		return user;
	}

	@Override
	public int updateUserInfo(final Long userId, final UserRegisterReqDto reqDto) {
		int updated = commandMapper.register(userId, reqDto);
		if (updated == 0) {
			throw new UserException(ErrorCode.USER_NOT_FOUND);
		}
		log.info("[User] 유저 회원가입 완료 - userId : {}", userId);
		return updated;
	}
}
