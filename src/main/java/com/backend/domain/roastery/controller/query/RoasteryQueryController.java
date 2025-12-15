package com.backend.domain.roastery.controller.query;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.backend.domain.roastery.dto.response.RoasteryResDto;
import com.backend.domain.roastery.service.facade.RoasteryFacadeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Tag(name = "Roastery", description = "로스터리 관리 API")
@RestController
@RequestMapping("/roasteries")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class RoasteryQueryController {

	private final RoasteryFacadeService roasteryFacadeService;

	@Operation(
		summary = "로스터리 상세 조회",
		description = "로스터리 ID로 로스터리의 상세 정보를 조회합니다."
	)
	@GetMapping("/{roasteryId}")
	public ResponseEntity<BaseResponse<RoasteryResDto>> getRoastery(@PathVariable Long roasteryId) {
		return ResponseUtils.ok(roasteryFacadeService.getRoastery(roasteryId));
	}

	@Operation(
		summary = "모든 로스터리 목록 조회",
		description = "전체 로스터리 목록을 페이지네이션으로 조회합니다."
	)
	@GetMapping
	public ResponseEntity<PageResponse<RoasteryResDto>> getAllRoasteries(
		@Parameter(description = "페이지 번호 (1부터 시작)", example = "1")
		@RequestParam(defaultValue = "1") @Min(1) int page,
		@Parameter(description = "페이지 크기", example = "10")
		@RequestParam(defaultValue = "10") @Min(1) int size
	) {
		Page<RoasteryResDto> response = roasteryFacadeService.getAllRoasteries(page, size);
		return ResponseUtils.page(response);
	}

	@Operation(
		summary = "로스터리 이름 검색",
		description = "로스터리 이름으로 검색하여 목록을 페이지네이션으로 조회합니다."
	)
	@GetMapping("/search")
	public ResponseEntity<PageResponse<RoasteryResDto>> searchRoasteriesByName(
		@Parameter(description = "검색 키워드", example = "블루보틀")
		@RequestParam @NotBlank String keyword,
		@Parameter(description = "페이지 번호 (1부터 시작)", example = "1")
		@RequestParam(defaultValue = "1") @Min(1) int page,
		@Parameter(description = "페이지 크기", example = "10")
		@RequestParam(defaultValue = "10") @Min(1) int size
	) {
		Page<RoasteryResDto> response = roasteryFacadeService.searchRoasteriesByName(keyword, page, size);
		return ResponseUtils.page(response);
	}

	@Operation(
		summary = "내가 관리하는 로스터리 목록 조회",
		description = "로그인한 ROASTERY_MANAGER가 소유한 로스터리 목록을 페이지네이션으로 조회합니다."
	)
	@PreAuthorize("hasRole('ROLE_ROASTERY_MANAGER')")
	@GetMapping("/my")
	public ResponseEntity<PageResponse<RoasteryResDto>> getMyRoasteries(
		@CurrentUser AuthenticatedUser token,
		@Parameter(description = "페이지 번호 (1부터 시작)", example = "1")
		@RequestParam(defaultValue = "1") @Min(1) int page,
		@Parameter(description = "페이지 크기", example = "10")
		@RequestParam(defaultValue = "10") @Min(1) int size
	) {
		Page<RoasteryResDto> response = roasteryFacadeService.getMyRoasteries(token.getUser().getId(), page, size);
		return ResponseUtils.page(response);
	}
}
