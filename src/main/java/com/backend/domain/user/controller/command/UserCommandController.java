package com.backend.domain.user.controller.command;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.annotation.CurrentUser;
import com.backend.common.auth.principal.AuthenticatedUser;
import com.backend.common.response.BaseResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.user.dto.request.UserRegisterReqDto;
import com.backend.domain.user.dto.response.UserInfoResDto;
import com.backend.domain.user.service.facade.UserFacadeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Tag(name = "User", description = "유저 관련 API")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCommandController {
	private final UserFacadeService userFacadeService;

	@Operation(
		summary = "사용자 서비스 등록",
		description = "소셜 로그인 후 닉네임과 역할을 설정하여 서비스에 최종 등록합니다. GUEST 상태의 사용자만 등록 가능합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "사용자 등록 성공"),
		@ApiResponse(responseCode = "400", description = "잘못된 요청 (닉네임 형식 오류, 필수값 누락)"),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
		@ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음"),
		@ApiResponse(responseCode = "409", description = "닉네임 중복")
	})
	@PostMapping("/register")
	public ResponseEntity<BaseResponse<UserInfoResDto>> userRegistration(
		@CurrentUser AuthenticatedUser token,
		@RequestBody @Valid UserRegisterReqDto reqDto
	) {
		UserInfoResDto response = userFacadeService.registerUser(token.getUser().getId(), reqDto);
		return ResponseUtils.ok(response);
	}
}