package com.backend.domain.review.controller.query;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.response.BaseResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.review.dto.response.ReviewedResDto;
import com.backend.domain.review.service.facade.ReviewFacadeService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Tag(name = "Review", description = "리뷰 관리 API")
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewQueryController {

	private final ReviewFacadeService reviewFacadeService;

	@GetMapping("{reviewId}")
	public ResponseEntity<BaseResponse<ReviewedResDto>> getReviewDetails(@PathVariable Long reviewId) {
		return ResponseUtils.ok(reviewFacadeService.getReviewDetails(reviewId));
	}
}
