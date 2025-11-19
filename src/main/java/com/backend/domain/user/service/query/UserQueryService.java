package com.backend.domain.user.service.query;

import com.backend.domain.user.dto.response.UserResDto;

public interface UserQueryService {

	UserResDto findById(Long userId);
}
