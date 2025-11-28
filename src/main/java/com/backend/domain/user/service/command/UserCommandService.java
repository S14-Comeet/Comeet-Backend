package com.backend.domain.user.service.command;

import com.backend.domain.user.entity.User;

public interface UserCommandService {
	User save(User user);
}
