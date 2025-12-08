package com.backend.domain.review.service.facade;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.ReviewException;
import com.backend.domain.review.converter.FlavorWheelConverter;
import com.backend.domain.review.converter.ReviewConverter;
import com.backend.domain.review.dto.common.FlavorWheelBadgeDto;
import com.backend.domain.review.dto.request.ReviewReqDto;
import com.backend.domain.review.dto.request.ReviewUpdateReqDto;
import com.backend.domain.review.dto.response.ReportResDto;
import com.backend.domain.review.dto.response.ReviewedResDto;
import com.backend.domain.review.entity.Review;
import com.backend.domain.review.factory.ReviewFactory;
import com.backend.domain.review.service.command.ReviewCommandService;
import com.backend.domain.review.service.command.TastingNoteCommandService;
import com.backend.domain.review.service.query.FlavorWheelQueryService;
import com.backend.domain.review.service.query.ReviewQueryService;
import com.backend.domain.review.validator.ReviewValidator;
import com.backend.domain.user.entity.User;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewFacadeService {

	private final TastingNoteCommandService tastingNoteCommandService;
	private final FlavorWheelQueryService flavorWheelQueryService;
	private final ReviewCommandService reviewCommandService;
	private final ReviewQueryService reviewQueryService;

	private final ReviewFactory reviewFactory;
	private final ReviewValidator reviewValidator;

	@Transactional
	public ReviewedResDto createReview(final User user, final ReviewReqDto reqDto) {
		Review review = processCreate(user.getId(), reqDto);
		tastingNoteCommandService.appendTastingNotes(review.getId(), reqDto.flavorWheelIdList());
		return createReviewedResDto(review, reqDto.flavorWheelIdList());
	}

	@Transactional
	public ReviewedResDto updateReview(final Long reviewId, final User user, final ReviewUpdateReqDto reqDto) {
		Review review = getValidatedReview(reviewId, user);
		processUpdate(reqDto, review);
		tastingNoteCommandService.overwriteTastingNotes(reviewId, reqDto.flavorWheelIdList());
		return createReviewedResDto(review, reqDto.flavorWheelIdList());
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

	private Review processCreate(final Long userId, final ReviewReqDto reqDto) {
		Review review = reviewFactory.create(userId, reqDto);
		reviewCommandService.insert(review);
		return review;
	}

	private ReviewedResDto createReviewedResDto(final Review review, final List<Long> flavorWheelIds) {
		List<FlavorWheelBadgeDto> badges = flavorWheelQueryService.findAllByIds(flavorWheelIds)
			.stream()
			.map(FlavorWheelConverter::toFlavorWheelBadgeDto)
			.toList();

		return ReviewConverter.toReviewedResDto(review, badges);
	}

	private Review getValidatedReview(final Long reviewId, final User user) {
		Review review = reviewQueryService.findById(reviewId);
		reviewValidator.validateReviewBelongsToUser(review, user.getId());
		return review;
	}

	private void processUpdate(final ReviewUpdateReqDto reqDto, final Review review) {
		review.update(reqDto);
		int affectedRows = reviewCommandService.update(review);
		if (affectedRows == 0) {
			throw new ReviewException(ErrorCode.REVIEW_UPDATE_FAILED);
		}
	}
}
