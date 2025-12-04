package com.backend.domain.user.converter;

import com.backend.domain.user.dto.response.NicknameDuplicateResDto;
import com.backend.domain.user.dto.response.UserInfoResDto;
import com.backend.domain.user.entity.User;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;

@UtilityClass
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserConverter {

	public UserInfoResDto toResponse(final User user) {
		return UserInfoResDto.builder()
			.userId(user.getId())
			.email(user.getEmail())
			.nickname(user.getNickname())
			.profileImageUrl(user.getProfileImageUrl())
			.build();
	}

	public NicknameDuplicateResDto toNicknameDuplicateResDto(final String nickname, final Boolean available) {
		return NicknameDuplicateResDto.builder()
			.nickname(nickname)
			.available(available)
			.build();
	}
}

