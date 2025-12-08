package com.backend.domain.review.service.query.implement;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.ReviewException;
import com.backend.domain.review.entity.Review;
import com.backend.domain.review.mapper.query.ReviewQueryMapper;
import com.backend.domain.review.service.query.ReviewQueryService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewQueryServiceImpl implements ReviewQueryService {
	private final ReviewQueryMapper queryMapper;

	@Override
	public Review findById(final Long reviewId) {
		return queryMapper.findById(reviewId)
			.orElseThrow(() -> new ReviewException(ErrorCode.REVIEW_NOT_FOUND));
	}
}
