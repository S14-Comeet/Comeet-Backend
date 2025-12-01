package com.backend.domain.map.controller.query;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.response.BaseResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.map.dto.response.MapMarkersResDto;
import com.backend.domain.map.service.query.MapQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Tag(name = "Map", description = "지도 API")
@RestController
@RequestMapping("/map")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
class MapQueryController {

	private final MapQueryService mapQueryService;

	@Operation(summary = "주변 매장 조회", description = "사용자 위치 기반으로 일정 거리 내의 매장 목록을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "주변 매장 조회 성공"),
		@ApiResponse(responseCode = "400", description = "잘못된 요청 파라미터")
	})
	@GetMapping("/markers")
	public ResponseEntity<BaseResponse<MapMarkersResDto>> getStoresWithinDistance(
		@Parameter(description = "사용자 위도", required = true, example = "37.5665")
		@RequestParam @DecimalMin("-90") @DecimalMax("90") final BigDecimal latitude,

		@Parameter(description = "사용자 경도", required = true, example = "126.9780")
		@RequestParam @DecimalMin("-180") @DecimalMax("180") final BigDecimal longitude,

		@Parameter(description = "최대 거리 (km), 미입력 시 1km 기본값 적용", required = false, example = "1.0")
		@RequestParam(required = false) @Positive final Double maxDistance
	) {
		final MapMarkersResDto response = mapQueryService.getStoresWithinDistance(latitude, longitude, maxDistance);
		return ResponseUtils.ok(response);
	}
}
