package com.backend.domain.beanscore.controller.command;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.response.BaseResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.beanscore.batch.BeanEmbeddingBatchService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Tag(name = "BeanScore Admin", description = "원두 점수 관리자 API")
@RestController
@RequestMapping("/api/admin/bean-scores")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class BeanEmbeddingController {

	private final BeanEmbeddingBatchService beanEmbeddingBatchService;

	@Operation(
		summary = "전체 원두 임베딩 생성",
		description = "모든 원두의 flavor_tags를 임베딩하여 Redis Vector에 저장합니다. 관리자 전용 API입니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "임베딩 생성 성공"),
		@ApiResponse(responseCode = "403", description = "관리자 권한 필요")
	})
	@PostMapping("/embed-all")
	public ResponseEntity<BaseResponse<EmbedAllResponse>> embedAllBeans() {
		int processedCount = beanEmbeddingBatchService.embedAllBeans();
		return ResponseUtils.ok(new EmbedAllResponse(processedCount, "전체 원두 임베딩 생성 완료"));
	}

	@Operation(
		summary = "누락된 원두 임베딩 생성",
		description = "임베딩이 없는 원두만 처리합니다. 관리자 전용 API입니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "임베딩 생성 성공"),
		@ApiResponse(responseCode = "403", description = "관리자 권한 필요")
	})
	@PostMapping("/embed-missing")
	public ResponseEntity<BaseResponse<EmbedAllResponse>> embedMissingBeans() {
		int processedCount = beanEmbeddingBatchService.embedMissingBeans();
		return ResponseUtils.ok(new EmbedAllResponse(processedCount, "누락된 원두 임베딩 생성 완료"));
	}

	public record EmbedAllResponse(
		int processedCount,
		String message
	) {
	}
}
