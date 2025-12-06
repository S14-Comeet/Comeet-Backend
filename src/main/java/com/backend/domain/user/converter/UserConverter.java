package com.backend.domain.user.converter;

import com.backend.domain.user.dto.response.NicknameDuplicateResDto;
import com.backend.domain.user.dto.response.UserInfoResDto;
import com.backend.domain.user.entity.User;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserConverter {

	public UserInfoResDto toResponse(final User user) {
		return UserInfoResDto.builder()
			.userId(user.getId())
			.name(user.getName())
			.email(user.getEmail())
			.nickname(user.getNickname())
			.profileImageUrl(user.getProfileImageUrl())
			.role(user.getRole())
			.build();
	}

	public NicknameDuplicateResDto toNicknameDuplicateResDto(final String nickname, final Boolean exists) {
		return NicknameDuplicateResDto.builder()
			.nickname(nickname)
			.exists(exists)
			.build();
	}
}

