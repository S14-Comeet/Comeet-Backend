package com.backend.domain.auth.service.command;

import com.backend.domain.user.entity.Role;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthCommandService {

	void logout(HttpServletRequest request, HttpServletResponse response);

	void reissue(HttpServletRequest request, HttpServletResponse response);

	void updateRole(String socialId, Role role);
}
