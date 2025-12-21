package com.backend.domain.recommendation.controller.query;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.annotation.CurrentUser;
import com.backend.common.auth.principal.AuthenticatedUser;
import com.backend.common.response.BaseResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.recommendation.dto.request.RecommendationReqDto;
import com.backend.domain.recommendation.dto.response.BeanRecommendationResDto;
import com.backend.domain.recommendation.dto.response.MenuRecommendationResDto;
import com.backend.domain.recommendation.enums.RecommendationType;
import com.backend.domain.recommendation.service.facade.RecommendationFacadeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Tag(name = "Recommendation", description = "추천 관련 API")
@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendationQueryController {

	private final RecommendationFacadeService recommendationFacadeService;

	@Operation(
		summary = "원두 추천",
		description = "사용자 취향에 맞는 원두 Top 3를 추천합니다. 벡터 유사도 검색과 LLM 리랭킹을 사용합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "추천 성공"),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
	})
	@GetMapping("/beans")
	public ResponseEntity<BaseResponse<List<BeanRecommendationResDto>>> recommendBeans(
		@CurrentUser AuthenticatedUser user
	) {
		List<BeanRecommendationResDto> recommendations = recommendationFacadeService.recommendBeans(
			user.getUser().getId()
		);
		return ResponseUtils.ok(recommendations);
	}

	@Operation(
		summary = "메뉴 추천 (전역)",
		description = "사용자 취향에 맞는 메뉴 Top 3를 추천합니다. 거리와 무관하게 전체 메뉴에서 추천합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "추천 성공"),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
	})
	@GetMapping("/menus")
	public ResponseEntity<BaseResponse<List<MenuRecommendationResDto>>> recommendMenus(
		@CurrentUser AuthenticatedUser user
	) {
		RecommendationReqDto reqDto = new RecommendationReqDto(
			RecommendationType.GLOBAL, null, null, null
		);
		List<MenuRecommendationResDto> recommendations = recommendationFacadeService.recommendMenus(
			user.getUser().getId(), reqDto
		);
		return ResponseUtils.ok(recommendations);
	}

	@Operation(
		summary = "메뉴 추천 (근거리)",
		description = "사용자 위치 기준 반경 내 카페의 메뉴 중 취향에 맞는 Top 3를 추천합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "추천 성공"),
		@ApiResponse(responseCode = "400", description = "위치 정보 누락"),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
	})
	@GetMapping("/menus/nearby")
	public ResponseEntity<BaseResponse<List<MenuRecommendationResDto>>> recommendNearbyMenus(
		@CurrentUser AuthenticatedUser user,
		@Parameter(description = "사용자 위도", required = true)
		@RequestParam BigDecimal latitude,
		@Parameter(description = "사용자 경도", required = true)
		@RequestParam BigDecimal longitude,
		@Parameter(description = "검색 반경 (km)", example = "5")
		@RequestParam(defaultValue = "5") Integer radiusKm
	) {
		RecommendationReqDto reqDto = new RecommendationReqDto(
			RecommendationType.LOCAL, latitude, longitude, radiusKm
		);
		List<MenuRecommendationResDto> recommendations = recommendationFacadeService.recommendMenus(
			user.getUser().getId(), reqDto
		);
		return ResponseUtils.ok(recommendations);
	}

	@Operation(
		summary = "특정 원두를 사용하는 메뉴 조회 (전역)",
		description = "특정 원두를 사용하는 모든 메뉴 목록을 조회합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공"),
		@ApiResponse(responseCode = "404", description = "원두를 찾을 수 없음")
	})
	@GetMapping("/beans/{beanId}/menus")
	public ResponseEntity<BaseResponse<List<MenuRecommendationResDto>>> findMenusByBean(
		@Parameter(description = "원두 ID", required = true)
		@PathVariable Long beanId
	) {
		RecommendationReqDto reqDto = new RecommendationReqDto(
			RecommendationType.GLOBAL, null, null, null
		);
		List<MenuRecommendationResDto> menus = recommendationFacadeService.findMenusByBean(beanId, reqDto);
		return ResponseUtils.ok(menus);
	}

	@Operation(
		summary = "특정 원두를 사용하는 메뉴 조회 (근거리)",
		description = "사용자 위치 기준 반경 내에서 특정 원두를 사용하는 메뉴 목록을 조회합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공"),
		@ApiResponse(responseCode = "400", description = "위치 정보 누락"),
		@ApiResponse(responseCode = "404", description = "원두를 찾을 수 없음")
	})
	@GetMapping("/beans/{beanId}/menus/nearby")
	public ResponseEntity<BaseResponse<List<MenuRecommendationResDto>>> findNearbyMenusByBean(
		@Parameter(description = "원두 ID", required = true)
		@PathVariable Long beanId,
		@Parameter(description = "사용자 위도", required = true)
		@RequestParam BigDecimal latitude,
		@Parameter(description = "사용자 경도", required = true)
		@RequestParam BigDecimal longitude,
		@Parameter(description = "검색 반경 (km)", example = "5")
		@RequestParam(defaultValue = "5") Integer radiusKm
	) {
		RecommendationReqDto reqDto = new RecommendationReqDto(
			RecommendationType.LOCAL, latitude, longitude, radiusKm
		);
		List<MenuRecommendationResDto> menus = recommendationFacadeService.findMenusByBean(beanId, reqDto);
		return ResponseUtils.ok(menus);
	}
}
