package com.backend.common.auth.principal;

import org.springframework.security.core.userdetails.UserDetails;

import com.backend.domain.user.entity.Role;
import com.backend.domain.user.entity.User;

public interface AuthenticatedUser {
	String getId();

	User getUser();

	Role getRole();
}
