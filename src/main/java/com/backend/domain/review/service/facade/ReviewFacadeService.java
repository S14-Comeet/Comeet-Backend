package com.backend.domain.review.service.facade;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.domain.review.converter.FlavorWheelConverter;
import com.backend.domain.review.converter.ReviewConverter;
import com.backend.domain.review.dto.common.FlavorWheelBadgeDto;
import com.backend.domain.review.dto.request.ReviewReqDto;
import com.backend.domain.review.dto.response.ReportResDto;
import com.backend.domain.review.dto.response.ReviewedResDto;
import com.backend.domain.review.entity.Review;
import com.backend.domain.review.factory.ReviewFactory;
import com.backend.domain.review.service.command.ReviewCommandService;
import com.backend.domain.review.service.command.TastingNoteCommandService;
import com.backend.domain.review.service.query.FlavorWheelQueryService;
import com.backend.domain.review.service.query.ReviewQueryService;
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

	@Transactional
	public ReviewedResDto createReview(final User user, final ReviewReqDto reqDto) {
		Review review = saveReview(user.getId(), reqDto);

		tastingNoteCommandService.appendTastingNotes(review.getId(), reqDto.flavorWheelIdList());

		List<FlavorWheelBadgeDto> badges = flavorWheelQueryService.findAllByIds(reqDto.flavorWheelIdList()).stream()
			.map(FlavorWheelConverter::toFlavorWheelBadgeDto)
			.toList();

		return ReviewConverter.toReviewedResDto(review, badges);
	}

	private Review saveReview(final Long userId, final ReviewReqDto reqDto) {
		Review review = reviewFactory.create(userId, reqDto);
		reviewCommandService.insert(review);
		return review;
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
