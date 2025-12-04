package com.backend.domain.user.service.query;

import java.util.Optional;

import com.backend.domain.user.dto.response.NicknameDuplicateResDto;
import com.backend.domain.user.dto.response.UserInfoResDto;
import com.backend.domain.user.entity.User;

public interface UserQueryService {

	UserInfoResDto findById(Long userId);

	Optional<User> findBySocialId(String socialId);

	NicknameDuplicateResDto checkNicknameDuplicate(String nickname);

	User findUnRegisterUserById(Long userID);
}
