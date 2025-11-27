package com.backend.domain.auth.service.command;

import com.backend.domain.auth.dto.response.LoginResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthCommandService {

	void logout(String refreshToken, HttpServletRequest request, HttpServletResponse response);

	void reissue(String refreshToken, HttpServletResponse response);

	void updateRole(String socialId, com.backend.domain.user.entity.Role role);
}
