package com.backend.domain.user.service.command;

import com.backend.domain.user.dto.request.UserReqDto;
import com.backend.domain.user.dto.response.UserResDto;
import com.backend.domain.user.entity.User;

public interface UserCommandService {
	UserResDto insert(UserReqDto reqDto);

	User save(User user);
}
