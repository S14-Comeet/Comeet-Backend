package com.backend.domain.visit.controller.command;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.annotation.CurrentUser;
import com.backend.common.auth.principal.AuthenticatedUser;
import com.backend.common.response.BaseResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.visit.dto.request.VerifyReqDto;
import com.backend.domain.visit.dto.response.VerifiedResDto;
import com.backend.domain.visit.service.facade.VisitFacadeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Tag(name = "Visit", description = "방문 인증 관련 API")
@RestController
@RequestMapping("/visit")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class VisitCommandController {

	private final VisitFacadeService visitFacadeService;

	@Operation(
		summary = "가게 방문 인증",
		description = "GPS 기반으로 가게 방문을 인증합니다. 사용자 위치가 가게로부터 100m 이내인 경우 인증 성공(verified=true), 100m 초과인 경우 인증 실패(verified=false)로 기록됩니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "방문 인증 처리 완료 (인증 성공/실패 결과 반환)"),
		@ApiResponse(responseCode = "400", description = "잘못된 요청 (좌표 범위 오류, 필수값 누락)"),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
		@ApiResponse(responseCode = "404", description = "사용자 또는 메뉴를 찾을 수 없음")
	})
	@PostMapping("/verify")
	public ResponseEntity<BaseResponse<VerifiedResDto>> verifyStoreVisit(
		@CurrentUser AuthenticatedUser token,
		@RequestBody @Valid VerifyReqDto reqDto
	) {
		return ResponseUtils.ok(visitFacadeService.verifyVisit(token.getUser(), reqDto));
	}

}
