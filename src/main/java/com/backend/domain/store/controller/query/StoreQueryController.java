package com.backend.domain.store.controller.query;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.response.BaseResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.store.dto.request.StoreSearchReqDto;
import com.backend.domain.store.dto.response.StoreListResDto;
import com.backend.domain.store.service.query.StoreQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Tag(name = "Store", description = "가맹점 API")
@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
class StoreQueryController {

	private final StoreQueryService storeQueryService;

	@Operation(
		summary = "가맹점 목록 조회",
		description = """
			사용자 위치 기반으로 주변 가맹점 목록을 조회합니다.
			- 거리순 정렬 (가까운 순)
			- 카테고리 및 키워드 필터링 지원
			- 리스트 뷰와 맵 뷰 모두에서 사용 가능 (마커 정보 포함)
			"""
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "가맹점 목록 조회 성공"),
		@ApiResponse(responseCode = "400", description = "잘못된 요청 파라미터")
	})
	@GetMapping
	public ResponseEntity<BaseResponse<StoreListResDto>> searchStores(
		@Parameter(
			description = "가맹점 검색 조건",
			required = true,
			schema = @Schema(implementation = StoreSearchReqDto.class)
		)
		@Valid @ModelAttribute final StoreSearchReqDto request
	) {
		final StoreListResDto response = storeQueryService.searchStores(request);
		return ResponseUtils.ok(response);
	}
}
