package com.backend.domain.visit.controller.query;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.annotation.CurrentUser;
import com.backend.common.auth.principal.AuthenticatedUser;
import com.backend.common.response.BaseResponse;
import com.backend.common.response.PageResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.visit.dto.common.VisitInfoDto;
import com.backend.domain.visit.dto.common.VisitPageDto;
import com.backend.domain.visit.service.facade.VisitFacadeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Tag(name = "Visit", description = "방문 인증 관련 API")
@RestController
@RequestMapping("/visit")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class VisitQueryController {

	private final VisitFacadeService visitFacadeService;

	@Operation(
		summary = "방문 인증 내역 목록 조회",
		description = "인증된 사용자의 방문 인증 내역을 페이지네이션하여 조회합니다. 최신순으로 정렬됩니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "방문 인증 내역 조회 성공"),
		@ApiResponse(responseCode = "400", description = "잘못된 요청 (페이지 번호 오류)"),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
	})
	@GetMapping("/history")
	public ResponseEntity<PageResponse<VisitPageDto>> getVisitHistory(
		@CurrentUser AuthenticatedUser token,
		@Parameter(description = "페이지 번호 (1부터 시작)", example = "1")
		@RequestParam(defaultValue = "1") @Min(1) int page,
		@Parameter(description = "페이지 크기", example = "10")
		@RequestParam(defaultValue = "10") @Min(1) int size
	) {
		Page<VisitPageDto> response = visitFacadeService.findAllWithPageableUserId(token.getUser(), page, size);
		return ResponseUtils.page(response);
	}

	@Operation(
		summary = "방문 인증 상세 조회",
		description = "특정 방문 인증 내역의 상세 정보를 조회합니다. 본인의 방문 인증 내역만 조회할 수 있습니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "방문 인증 상세 조회 성공"),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
		@ApiResponse(responseCode = "403", description = "접근 권한 없음 (다른 사용자의 방문 인증 내역)"),
		@ApiResponse(responseCode = "404", description = "방문 인증 내역을 찾을 수 없음")
	})
	@GetMapping("/{visitId}")
	public ResponseEntity<BaseResponse<VisitInfoDto>> getVisitById(
		@CurrentUser AuthenticatedUser token,
		@Parameter(description = "방문 인증 ID", example = "1", required = true)
		@PathVariable Long visitId
	) {
		VisitInfoDto response = visitFacadeService.findVisitById(token.getUser(), visitId);
		return ResponseUtils.ok(response);
	}
}

