package com.backend.domain.auth.controller.query;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.response.BaseResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.auth.dto.response.CheckNicknameDuplicateResponse;
import com.backend.domain.auth.service.query.AuthQueryServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthQueryController {

	private final AuthQueryServiceImpl queryService;

	@GetMapping("/nickName/check")
	@Operation(
		summary = "닉네임 중복 확인 API",
		description = "회원가입 시 닉네임 중복 여부를 확인합니다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "중복 확인 성공"),
			@ApiResponse(responseCode = "400", description = "사용자 입력 오류[C-001]")
		}
	)
	public ResponseEntity<BaseResponse<CheckNicknameDuplicateResponse>> checkNicknameDuplicate(
		@RequestParam @NotBlank(message = "닉네임은 필수 입력 항목입니다.")
		String nickname
	) {
		CheckNicknameDuplicateResponse response = queryService.checkNicknameDuplicate(nickname);
		return ResponseUtils.ok(response);
	}
}
