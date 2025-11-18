package com.backend.domain.user.service.query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.UserException;
import com.backend.domain.user.converter.UserConverter;
import com.backend.domain.user.dto.response.UserResDto;
import com.backend.domain.user.entity.User;
import com.backend.domain.user.mapper.query.UserQueryMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserQueryServiceImpl implements UserQueryService {

	private final UserQueryMapper queryMapper;

	@Override
	public UserResDto findById(final Long userId) {
		User user = queryMapper.findById(userId)
			.orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
		log.info("[User] 사용자 조회 완료 - userId: {}", user.getId());
		return UserConverter.toResponse(user);
	}
}
