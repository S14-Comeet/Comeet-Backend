package com.backend.domain.user.validator;

import org.springframework.stereotype.Component;

import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.UserException;
import com.backend.common.validator.Validator;
import com.backend.domain.user.dto.request.UserRegisterReqDto;
import com.backend.domain.user.entity.Role;
import com.backend.domain.user.entity.User;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserValidator implements Validator<User> {

	private static final String NICKNAME_REGEX = "^[a-zA-Z가-힣0-9]++(?: [a-zA-Z가-힣0-9]++)*+$";

	@Override
	public void validate(final User user) {
		validateUserId(user.getId());
		validateUserRole(user.getRole());
	}

	public void validate(final UserRegisterReqDto reqDto) {
		validateNickname(reqDto.nickname());
	}

	public void validateNickname(final String nickname) {
		if (nickname == null || nickname.isBlank()) {
			throw new UserException(ErrorCode.NICKNAME_NOT_BLANK);
		}
		if (!nickname.matches(NICKNAME_REGEX)) {
			throw new UserException(ErrorCode.NICKNAME_INVALID_FORMAT);
		}
	}

	private void validateUserId(final Long userId) {
		if (userId == null) {
			throw new UserException(ErrorCode.USER_NOT_FOUND);
		}
	}

	private void validateUserRole(final Role role) {
		if (role == null || Role.isNotActiveUser(role)) {
			throw new UserException(ErrorCode.ACCESS_DENIED);
		}
	}
}