package com.backend.domain.auth.controller.command;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.auth.principal.AuthenticatedUser;
import com.backend.common.response.BaseResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.auth.dto.request.UpdateRoleRequest;
import com.backend.domain.auth.service.command.AuthCommandService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthCommandController {

	private final AuthCommandService authCommandService;

	@PostMapping("/logout")
	public ResponseEntity<BaseResponse<Void>> logout(
		@CookieValue(name = "refreshToken") String refreshToken,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		authCommandService.logout(refreshToken, request, response);
		return ResponseUtils.noContent();
	}

	@PostMapping("/reissue")
	public ResponseEntity<BaseResponse<Void>> reissueToken(
		@CookieValue(name = "refreshToken") String refreshToken,
		HttpServletResponse response
	) {
		authCommandService.reissue(refreshToken, response);
		return ResponseUtils.noContent();
	}

	@PatchMapping("/role")
	public ResponseEntity<BaseResponse<Void>> updateRole(
		@AuthenticationPrincipal AuthenticatedUser user,
		@Valid @RequestBody UpdateRoleRequest request
	) {
		authCommandService.updateRole(user.getId(), request.getRole());
		return ResponseUtils.noContent();
	}
}
