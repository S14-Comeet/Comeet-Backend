package com.backend.domain.review.service.facade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.domain.review.dto.request.ReviewReqDto;
import com.backend.domain.review.dto.response.ReportResDto;
import com.backend.domain.review.dto.response.ReviewedResDto;
import com.backend.domain.review.service.command.ReviewCommandService;
import com.backend.domain.review.service.query.ReviewQueryService;
import com.backend.domain.user.entity.User;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewFacadeService {

	private final ReviewCommandService reviewCommandService;
	private final ReviewQueryService reviewQueryService;

	@Transactional
	public ReviewedResDto createReview(final User user, final ReviewReqDto reqDto) {
		//TODO reviewCommandService.saveReview();
		return null;
	}

	@Transactional
	public ReviewedResDto updateReview(final Long reviewId, final User user, final ReviewReqDto reqDto) {
		//TODO reviewCommandService.updateReview(reviewId, reqDto);
		return null;
	}

	@Transactional
	public void deleteReview(final Long reviewId, final User user) {
		//TODO reviewCommandService.deleteReview(reviewId);
	}

	@Transactional
	public ReportResDto reportReview(final Long reviewId, final User user) {
		//TODO reviewCommandService.reportReview(reviewId);
		return null;
	}

	@Transactional(readOnly = true)
	public ReviewedResDto getReviewDetails(final Long reviewId, final User user) {
		//TODO reviewQueryService.findReviewById(reviewId);
		return null;
	}
}
