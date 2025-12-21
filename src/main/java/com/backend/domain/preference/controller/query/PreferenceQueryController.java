package com.backend.domain.preference.controller.query;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.annotation.CurrentUser;
import com.backend.common.auth.principal.AuthenticatedUser;
import com.backend.common.response.BaseResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.preference.dto.response.PreferenceResDto;
import com.backend.domain.preference.service.facade.PreferenceFacadeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Tag(name = "Preference", description = "사용자 취향 관련 API")
@RestController
@RequestMapping("/api/preferences")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PreferenceQueryController {

	private final PreferenceFacadeService preferenceFacadeService;

	@Operation(
		summary = "사용자 취향 조회",
		description = "현재 로그인한 사용자의 커피 취향 정보를 조회합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "취향 조회 성공"),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
		@ApiResponse(responseCode = "404", description = "취향 정보를 찾을 수 없음")
	})
	@GetMapping
	public ResponseEntity<BaseResponse<PreferenceResDto>> getPreference(
		@CurrentUser AuthenticatedUser user
	) {
		PreferenceResDto response = preferenceFacadeService.getPreference(user.getUser().getId());
		return ResponseUtils.ok(response);
	}
}
