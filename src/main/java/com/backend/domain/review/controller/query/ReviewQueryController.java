package com.backend.domain.review.controller.query;

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
import com.backend.domain.review.dto.common.ReviewPageDto;
import com.backend.domain.review.dto.response.ReviewedResDto;
import com.backend.domain.review.service.facade.ReviewFacadeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Tag(name = "Review", description = "리뷰 관리 API")
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewQueryController {

	private final ReviewFacadeService reviewFacadeService;

	@Operation(
		summary = "리뷰 상세 조회",
		description = "리뷰 ID로 리뷰의 상세 정보를 조회합니다. 리뷰 내용, 이미지, FlavorWheel 뱃지 등을 포함합니다."
	)
	@GetMapping("{reviewId}")
	public ResponseEntity<BaseResponse<ReviewedResDto>> getReviewDetails(@PathVariable Long reviewId) {
		return ResponseUtils.ok(reviewFacadeService.getReviewDetails(reviewId));
	}

	@Operation(
		summary = "내 리뷰 목록 조회 (페이지네이션)",
		description = "로그인한 사용자의 리뷰 목록을 페이지네이션으로 조회합니다. 각 리뷰에 연결된 FlavorWheel 뱃지도 함께 조회됩니다. N+1 쿼리를 방지하기 위해 배치 조회를 사용합니다."
	)
	@GetMapping
	public ResponseEntity<PageResponse<ReviewPageDto>> getReviews(
		@CurrentUser AuthenticatedUser token,
		@Parameter(description = "페이지 번호 (1부터 시작)", example = "1")
		@RequestParam(defaultValue = "1") @Min(1) int page,
		@Parameter(description = "페이지 크기", example = "10")
		@RequestParam(defaultValue = "10") @Min(1) int size
	) {
		Page<ReviewPageDto> response = reviewFacadeService.findAllWithPageableByUserId(token.getUser(), page, size);
		return ResponseUtils.page(response);
	}
}
