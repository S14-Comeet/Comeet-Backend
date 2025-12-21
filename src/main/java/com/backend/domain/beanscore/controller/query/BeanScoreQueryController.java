package com.backend.domain.beanscore.controller.query;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.response.BaseResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.beanscore.dto.response.BeanScoreResDto;
import com.backend.domain.beanscore.service.facade.BeanScoreFacadeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Tag(name = "BeanScore", description = "원두 점수 관련 API")
@RestController
@RequestMapping("/api/bean-scores")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class BeanScoreQueryController {

	private final BeanScoreFacadeService beanScoreFacadeService;

	@Operation(
		summary = "원두 점수 조회",
		description = "특정 원두의 점수 정보를 조회합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "원두 점수 조회 성공"),
		@ApiResponse(responseCode = "404", description = "원두 점수를 찾을 수 없음")
	})
	@GetMapping("/{beanId}")
	public ResponseEntity<BaseResponse<BeanScoreResDto>> getBeanScore(
		@Parameter(description = "원두 ID", required = true)
		@PathVariable Long beanId
	) {
		BeanScoreResDto response = beanScoreFacadeService.getBeanScore(beanId);
		return ResponseUtils.ok(response);
	}
}
