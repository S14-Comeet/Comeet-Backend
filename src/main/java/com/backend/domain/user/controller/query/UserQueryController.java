package com.backend.domain.user.controller.query;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.annotation.CurrentUser;
import com.backend.common.auth.principal.AuthenticatedUser;
import com.backend.common.response.BaseResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.user.dto.response.NicknameDuplicateResDto;
import com.backend.domain.user.dto.response.UserInfoResDto;
import com.backend.domain.user.service.query.UserQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Tag(name = "User", description = "유저 관련 API")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserQueryController {
	private final UserQueryService userQueryService;

	@Operation(summary = "사용자 정보 조회", description = "인증된 사용자의 정보를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공"),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
		@ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
	})
	@GetMapping
	public ResponseEntity<BaseResponse<UserInfoResDto>> findUser(
		@CurrentUser AuthenticatedUser user
	) {
		UserInfoResDto response = userQueryService.findById(user.getUser().getId());
		return ResponseUtils.ok(response);
	}


	@GetMapping("/nickname/check")
	public ResponseEntity<BaseResponse<NicknameDuplicateResDto>> checkNicknameDuplicate(
		@NotBlank @RequestParam String nickname
	) {
		NicknameDuplicateResDto response = userQueryService.checkNicknameDuplicate(nickname);
		return ResponseUtils.ok(response);
	}
}
