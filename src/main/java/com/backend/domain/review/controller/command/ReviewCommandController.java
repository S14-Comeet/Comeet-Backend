package com.backend.domain.review.controller.command;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.annotation.CurrentUser;
import com.backend.common.auth.principal.AuthenticatedUser;
import com.backend.common.response.BaseResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.review.dto.request.ReviewReqDto;
import com.backend.domain.review.dto.request.ReviewUpdateReqDto;
import com.backend.domain.review.dto.response.ReportResDto;
import com.backend.domain.review.dto.response.ReviewedResDto;
import com.backend.domain.review.service.facade.ReviewFacadeService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Tag(name = "Review", description = "리뷰 관리 API")
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewCommandController {

	private final ReviewFacadeService reviewFacadeService;

	@PostMapping
	public ResponseEntity<BaseResponse<ReviewedResDto>> saveReview(
		@CurrentUser AuthenticatedUser token,
		@RequestBody @Valid ReviewReqDto reqDto
	) {
		return ResponseUtils.ok(reviewFacadeService.createReview(token.getUser(), reqDto));
	}

	@PatchMapping("/{reviewId}")
	public ResponseEntity<BaseResponse<ReviewedResDto>> updatedReview(
		@PathVariable Long reviewId,
		@CurrentUser AuthenticatedUser token,
		@RequestBody @Valid ReviewUpdateReqDto reqDto
	) {
		return ResponseUtils.ok(reviewFacadeService.updateReview(reviewId, token.getUser(), reqDto));
	}

	@DeleteMapping("/{reviewId}")
	public ResponseEntity<BaseResponse<Void>> deleteReview(
		@PathVariable Long reviewId,
		@CurrentUser AuthenticatedUser token
	) {
		reviewFacadeService.deleteReview(reviewId, token.getUser());
		return ResponseUtils.noContent();
	}

	/**
	 * @deprecated 이 기능은 관리자 기능이 먼저 구현되어야 합니다.
	 */
	@Deprecated(since = "1.0")
	@PostMapping("/{reviewId}/report")
	public ResponseEntity<BaseResponse<ReportResDto>> reportReview(
		@PathVariable Long reviewId,
		@CurrentUser AuthenticatedUser token
	) {
		return ResponseUtils.ok(reviewFacadeService.reportReview(reviewId, token.getUser()));
	}
}
