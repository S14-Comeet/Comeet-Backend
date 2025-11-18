package com.backend.domain.user.converter;


import com.backend.domain.user.dto.request.UserReqDto;
import com.backend.domain.user.dto.response.UserResDto;
import com.backend.domain.user.entity.User;
public class UserConverter {

	public static User toEntity(UserReqDto reqDto) {
		return User.builder()
			.build();
	}

	public static UserResDto toResponse(User user) {
		return UserResDto.builder()
			.build();
	}
}
