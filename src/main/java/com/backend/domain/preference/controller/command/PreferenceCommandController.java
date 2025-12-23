package com.backend.domain.preference.controller.command;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.annotation.CurrentUser;
import com.backend.common.auth.principal.AuthenticatedUser;
import com.backend.common.response.BaseResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.preference.dto.request.PreferenceUpdateReqDto;
import com.backend.domain.preference.dto.response.PreferenceResDto;
import com.backend.domain.preference.service.facade.PreferenceFacadeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Tag(name = "Preference", description = "사용자 취향 관련 API")
@RestController
@RequestMapping("/preferences")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PreferenceCommandController {

	private final PreferenceFacadeService preferenceFacadeService;

	@Operation(
		summary = "사용자 취향 초기화",
		description = "사용자의 커피 취향을 기본값으로 초기화합니다. 이미 취향 정보가 있으면 에러가 발생합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "취향 초기화 성공"),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
		@ApiResponse(responseCode = "409", description = "이미 취향 정보가 존재함")
	})
	@PostMapping("/init")
	public ResponseEntity<BaseResponse<PreferenceResDto>> initPreference(
		@CurrentUser AuthenticatedUser user
	) {
		PreferenceResDto response = preferenceFacadeService.initPreference(user.getUser().getId());
		return ResponseUtils.created(response);
	}

	@Operation(
		summary = "사용자 취향 업데이트",
		description = "사용자의 커피 취향을 업데이트합니다. 취향 정보가 없으면 자동으로 생성됩니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "취향 업데이트 성공"),
		@ApiResponse(responseCode = "400", description = "잘못된 요청 (유효성 검증 실패)"),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
	})
	@PutMapping
	public ResponseEntity<BaseResponse<PreferenceResDto>> updatePreference(
		@CurrentUser AuthenticatedUser user,
		@RequestBody @Valid PreferenceUpdateReqDto reqDto
	) {
		PreferenceResDto response = preferenceFacadeService.updatePreference(user.getUser().getId(), reqDto);
		return ResponseUtils.ok(response);
	}

	@Operation(
		summary = "사용자 취향 삭제",
		description = "사용자의 커피 취향 정보를 삭제합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "취향 삭제 성공"),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
		@ApiResponse(responseCode = "404", description = "취향 정보를 찾을 수 없음")
	})
	@DeleteMapping
	public ResponseEntity<Void> deletePreference(
		@CurrentUser AuthenticatedUser user
	) {
		preferenceFacadeService.deletePreference(user.getUser().getId());
		return ResponseEntity.noContent().build();
	}
}
