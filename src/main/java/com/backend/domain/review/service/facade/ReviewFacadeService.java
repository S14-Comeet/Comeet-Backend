package com.backend.domain.review.service.facade;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.ReviewException;
import com.backend.common.util.PageUtils;
import com.backend.domain.flavor.converter.FlavorConverter;
import com.backend.domain.flavor.dto.common.FlavorBadgeDto;
import com.backend.domain.flavor.service.FlavorQueryService;
import com.backend.domain.review.converter.ReviewConverter;
import com.backend.domain.review.dto.common.ReviewFlavorDto;
import com.backend.domain.review.dto.common.ReviewPageDto;
import com.backend.domain.review.dto.request.ReviewReqDto;
import com.backend.domain.review.dto.request.ReviewUpdateReqDto;
import com.backend.domain.review.dto.response.ReportResDto;
import com.backend.domain.review.dto.response.ReviewedResDto;
import com.backend.domain.review.entity.Review;
import com.backend.domain.review.factory.ReviewFactory;
import com.backend.domain.review.mapper.query.TastingNoteQueryMapper;
import com.backend.domain.review.service.command.ReviewCommandService;
import com.backend.domain.review.service.command.TastingNoteCommandService;
import com.backend.domain.review.service.query.ReviewQueryService;
import com.backend.domain.review.validator.ReviewValidator;
import com.backend.domain.user.entity.User;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewFacadeService {

	private final TastingNoteCommandService tastingNoteCommandService;
	private final FlavorQueryService flavorQueryService;

	private final ReviewCommandService reviewCommandService;
	private final ReviewQueryService reviewQueryService;
	private final ReviewValidator reviewValidator;
	private final ReviewFactory reviewFactory;

	private final TastingNoteQueryMapper tastingNoteQueryMapper;

	public ReviewedResDto createReview(final Long userId, final ReviewReqDto reqDto) {
		validateVisitIdNotDuplicate(reqDto.visitId());
		Review review = processCreateReview(userId, reqDto);
		tastingNoteCommandService.appendTastingNotes(review.getId(), reqDto.flavorIdList());
		return createReviewedResDto(review, reqDto.flavorIdList());
	}

	private void validateVisitIdNotDuplicate(final Long visitId) {
		if (reviewQueryService.existsByVisitId(visitId)) {
			throw new ReviewException(ErrorCode.REVIEW_ALREADY_EXISTS_FOR_VISIT);
		}
	}

	public ReviewedResDto updateReview(final Long reviewId, final User user, final ReviewUpdateReqDto reqDto) {
		Review review = getValidatedReview(reviewId, user);
		processUpdateReview(reqDto, review);
		tastingNoteCommandService.overwriteTastingNotes(reviewId, reqDto.flavorIdList());
		return createReviewedResDto(review, reqDto.flavorIdList());
	}

	@Transactional
	public void deleteReview(final Long reviewId, final User user) {
		Review review = getValidatedReview(reviewId, user);
		int affectedRows = reviewCommandService.softDelete(review.getId());
		if (affectedRows == 0) {
			throw new ReviewException(ErrorCode.REVIEW_SOFT_DELETE_FAILED);
		}
	}

	public ReportResDto reportReview(final Long reviewId, final User user) {
		// ! 관리자 기능 구현이 선행되어야한다.
		return null;
	}

	public ReviewedResDto getReviewDetails(final Long reviewId) {
		Review review = reviewQueryService.findById(reviewId);
		List<FlavorBadgeDto> badges = flavorQueryService.findFlavorsByReviewId(reviewId).stream()
			.map(FlavorConverter::toFlavorBadgeDto)
			.toList();
		return ReviewConverter.toReviewedResDto(review, badges);
	}

	public Page<ReviewPageDto> findAllWithPageableByUserId(final User user, final int page, final int size) {
		Pageable pageable = PageUtils.getPageable(page, size);
		List<Review> reviews = reviewQueryService.findAllByUserId(user.getId(), pageable);

		if (reviews.isEmpty()) {
			return PageUtils.toPage(List.of(), pageable, 0);
		}

		int total = reviewQueryService.countAllByUserId(user.getId());
		List<ReviewPageDto> reviewPageDtos = buildReviewPageDtos(reviews);

		return PageUtils.toPage(reviewPageDtos, pageable, total);
	}

	public Page<ReviewPageDto> findAllWithPageableByStoreId(final Long storeId, final int page, final int size) {
		Pageable pageable = PageUtils.getPageable(page, size);
		List<Review> reviews = reviewQueryService.findAllByStoreId(storeId, pageable);

		if (reviews.isEmpty()) {
			return PageUtils.toPage(List.of(), pageable, 0);
		}

		int total = reviewQueryService.countAllByStoreId(storeId);
		List<ReviewPageDto> reviewPageDtos = buildReviewPageDtos(reviews);

		return PageUtils.toPage(reviewPageDtos, pageable, total);
	}

	private Review processCreateReview(final Long userId, final ReviewReqDto reqDto) {
		Review review = reviewFactory.create(userId, reqDto);
		reviewCommandService.insert(review);
		return review;
	}

	private ReviewedResDto createReviewedResDto(final Review review, final List<Long> flavorIds) {
		List<FlavorBadgeDto> badges = flavorQueryService.findAllByIds(flavorIds)
			.stream()
			.map(FlavorConverter::toFlavorBadgeDto)
			.toList();

		return ReviewConverter.toReviewedResDto(review, badges);
	}

	private Review getValidatedReview(final Long reviewId, final User user) {
		Review review = reviewQueryService.findById(reviewId);
		reviewValidator.validateReviewBelongsToUser(review, user.getId());
		reviewValidator.validateReviewNotDeleted(review);
		return review;
	}

	private void processUpdateReview(final ReviewUpdateReqDto reqDto, final Review review) {
		review.update(reqDto);
		int affectedRows = reviewCommandService.update(review);
		if (affectedRows == 0) {
			throw new ReviewException(ErrorCode.REVIEW_UPDATE_FAILED);
		}
	}

	private List<ReviewPageDto> buildReviewPageDtos(final List<Review> reviews) {
		// * 리뷰 ID 목록 추출
		List<Long> reviewIds = reviews.stream()
			.map(Review::getId)
			.toList();

		List<ReviewFlavorDto> reviewFlavors = tastingNoteQueryMapper.findFlavorIdsByReviewIds(reviewIds);

		// * Flavor ID를 모아서 한 번에 조회
		List<Long> allFlavorIds = reviewFlavors.stream()
			.map(ReviewFlavorDto::flavorId)
			.distinct()
			.toList();

		// * Flavor 정보를 한 번에 조회하고 Map으로 변환
		Map<Long, FlavorBadgeDto> flavorMap = flavorQueryService.findAllByIds(allFlavorIds)
			.stream()
			.map(FlavorConverter::toFlavorBadgeDto)
			.collect(Collectors.toMap(FlavorBadgeDto::flavorId, badge -> badge));

		// * ReviewId별로 Flavor 그룹화
		Map<Long, List<FlavorBadgeDto>> reviewBadgesMap = reviewFlavors.stream()
			.collect(Collectors.groupingBy(
				ReviewFlavorDto::reviewId,
				Collectors.mapping(
					dto -> flavorMap.get(dto.flavorId()),
					Collectors.filtering(Objects::nonNull, Collectors.toList())
				)
			));

		// * 리뷰별로 Flavor 뱃지 매핑하여 DTO 변환
		return reviews.stream()
			.map(review -> {
				List<FlavorBadgeDto> badges = reviewBadgesMap.getOrDefault(review.getId(), List.of());
				return ReviewConverter.toReviewPageDto(review, badges);
			})
			.toList();
	}
}
