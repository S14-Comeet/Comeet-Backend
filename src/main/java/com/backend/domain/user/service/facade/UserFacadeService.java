package com.backend.domain.user.service.facade;

import org.springframework.stereotype.Service;

import com.backend.domain.user.dto.request.UserRegisterReqDto;
import com.backend.domain.user.dto.response.NicknameDuplicateResDto;
import com.backend.domain.user.dto.response.UserInfoResDto;
import com.backend.domain.user.service.command.UserCommandService;
import com.backend.domain.user.service.query.UserQueryService;
import com.backend.domain.user.validator.UserValidator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserFacadeService {

	private final UserCommandService commandService;
	private final UserQueryService queryService;
	private final UserValidator userValidator;

	public UserInfoResDto registerUser(Long userId, UserRegisterReqDto reqDto) {
		userValidator.validate(reqDto);
		commandService.updateUserInfo(userId, reqDto);
		return queryService.findById(userId);
	}

	public UserInfoResDto findUserInfo(Long userId) {
		return queryService.findById(userId);
	}

	public NicknameDuplicateResDto checkNicknameDuplicate(String nickname) {
		return queryService.checkNicknameDuplicate(nickname);
	}
}
