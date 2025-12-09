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

import io.swagger.v3.oas.annotations.Operation;
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

	@Operation(
		summary = "리뷰 작성",
		description = "방문 기록과 메뉴에 대한 리뷰를 작성합니다. 리뷰 내용, 이미지, 공개 여부, FlavorWheel 뱃지를 선택할 수 있습니다."
	)
	@PostMapping
	public ResponseEntity<BaseResponse<ReviewedResDto>> saveReview(
		@CurrentUser AuthenticatedUser token,
		@RequestBody @Valid ReviewReqDto reqDto
	) {
		return ResponseUtils.ok(reviewFacadeService.createReview(token.getUser(), reqDto));
	}

	@Operation(
		summary = "리뷰 수정",
		description = "작성한 리뷰를 수정합니다. 본인이 작성한 리뷰만 수정할 수 있습니다."
	)
	@PatchMapping("/{reviewId}")
	public ResponseEntity<BaseResponse<ReviewedResDto>> updatedReview(
		@PathVariable Long reviewId,
		@CurrentUser AuthenticatedUser token,
		@RequestBody @Valid ReviewUpdateReqDto reqDto
	) {
		return ResponseUtils.ok(reviewFacadeService.updateReview(reviewId, token.getUser(), reqDto));
	}

	@Operation(
		summary = "리뷰 삭제",
		description = "작성한 리뷰를 삭제합니다. 본인이 작성한 리뷰만 삭제할 수 있으며, Soft Delete 방식으로 처리됩니다."
	)
	@DeleteMapping("/{reviewId}")
	public ResponseEntity<BaseResponse<Void>> deleteReview(
		@PathVariable Long reviewId,
		@CurrentUser AuthenticatedUser token
	) {
		reviewFacadeService.deleteReview(reviewId, token.getUser());
		return ResponseUtils.noContent();
	}

	@Deprecated(since = "1.0")
	@Operation(
		summary = "리뷰 신고 (미구현)",
		description = "부적절한 리뷰를 신고합니다. 관리자 기능이 구현된 후 사용 가능합니다."
	)
	@PostMapping("/{reviewId}/report")
	public ResponseEntity<BaseResponse<ReportResDto>> reportReview(
		@PathVariable Long reviewId,
		@CurrentUser AuthenticatedUser token
	) {
		return ResponseUtils.ok(reviewFacadeService.reportReview(reviewId, token.getUser()));
	}
}
