package com.backend.domain.auth.service.query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.domain.auth.converter.AuthConverter;
import com.backend.domain.auth.dto.response.CheckNicknameDuplicateResponse;
import com.backend.domain.user.mapper.query.UserQueryMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthQueryServiceImpl implements AuthQueryService{

	public UserQueryMapper userQueryMapper;

	public CheckNicknameDuplicateResponse checkNicknameDuplicate(final String nickname) {
		Boolean available = userQueryMapper.existByNickName(nickname);
		return AuthConverter.toCheckNicknameDuplicateResponse(nickname, available);
	}
}
