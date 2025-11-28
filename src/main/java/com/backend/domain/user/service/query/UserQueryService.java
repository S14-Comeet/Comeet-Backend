package com.backend.domain.user.service.query;

import java.util.Optional;

import com.backend.domain.user.dto.response.UserResDto;
import com.backend.domain.user.entity.User;

public interface UserQueryService {

	UserResDto findById(Long userId);

	User findByEmail(String email);

	Optional<User> findBySocialId(String socialId);

	Boolean existBySocialId(String socialId);
}
