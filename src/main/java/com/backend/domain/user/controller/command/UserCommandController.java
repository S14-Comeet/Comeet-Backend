package com.backend.domain.user.controller.command;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.annotation.CurrentUser;
import com.backend.common.auth.principal.AuthenticatedUser;
import com.backend.common.response.BaseResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.user.dto.request.UserRegisterReqDto;
import com.backend.domain.user.dto.response.UserInfoResDto;
import com.backend.domain.user.service.facade.UserFacadeService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCommandController {
	private final UserFacadeService userFacadeService;

	@PostMapping("/register")
	public ResponseEntity<BaseResponse<UserInfoResDto>> userRegistration(
		@CurrentUser AuthenticatedUser token,
		UserRegisterReqDto reqDto
	) {
		UserInfoResDto response = userFacadeService.registerUser(token.getUser().getId(), reqDto);
		return ResponseUtils.ok(response);
	}
}