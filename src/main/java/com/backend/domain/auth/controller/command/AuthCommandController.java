package com.backend.domain.auth.controller.command;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.annotation.CurrentUser;
import com.backend.common.auth.principal.AuthenticatedUser;
import com.backend.common.response.BaseResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.auth.dto.request.UpdateRoleRequest;
import com.backend.domain.auth.service.command.AuthCommandService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Tag(name = "Auth", description = "인증 관련 API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthCommandController {

	private final AuthCommandService authCommandService;

	@Operation(summary = "로그아웃", description = "액세스 토큰과 리프레시 토큰을 블랙리스트에 추가하고 Redis에서 삭제합니다.")
	@PostMapping("/logout")
	public ResponseEntity<BaseResponse<Void>> logout(
		HttpServletRequest request,
		HttpServletResponse response
	) {
		authCommandService.logout(request, response);
		return ResponseUtils.noContent();
	}

	@Operation(summary = "토큰 재발급", description = "RefreshToken을 사용하여 새로운 AccessToken과 RefreshToken을 발급합니다.")
	@PostMapping("/reissue")
	public ResponseEntity<BaseResponse<Void>> reissueToken(
		HttpServletRequest request,
		HttpServletResponse response
	) {
		authCommandService.reissue(request, response);
		return ResponseUtils.noContent();
	}

	@Operation(summary = "사용자 Role 업데이트", description = "로그인 후 사용자의 Role을 USER 또는 OWNER로 변경합니다.")
	@PatchMapping("/role")
	public ResponseEntity<BaseResponse<Void>> updateRole(
		@CurrentUser AuthenticatedUser user,
		@Valid @RequestBody UpdateRoleRequest request
	) {
		authCommandService.updateRole(user.getId(), request.getRole());
		return ResponseUtils.ok(null);
	}
}
