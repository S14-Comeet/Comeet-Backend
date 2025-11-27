package com.backend.domain.user.service.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.domain.user.entity.User;
import com.backend.domain.user.mapper.command.UserCommandMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCommandServiceImpl implements UserCommandService {

	private final UserCommandMapper commandMapper;

	@Override
	public User save(final User user) {
		int changes = commandMapper.save(user);
		log.info("[User] 유저생성 완료 - 추가된 항목 {} 개 userId : {}", changes, user.getEmail());
		return user;
	}
}
