package com.backend.domain.flavor.service.query;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.ReviewException;
import com.backend.domain.flavor.entity.FlavorWheel;
import com.backend.domain.flavor.mapper.query.FlavorWheelQueryMapper;

import io.jsonwebtoken.lang.Collections;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class FlavorWheelQueryServiceImpl implements FlavorWheelQueryService {
	private final FlavorWheelQueryMapper queryMapper;

	@Override
	public List<FlavorWheel> findAllByIds(final List<Long> flavorWheelIdList) {
		if (Collections.isEmpty(flavorWheelIdList)) {
			return List.of();
		}
		List<FlavorWheel> flavorWheels = queryMapper.findAllByIds(flavorWheelIdList);
		log.info("[Review] FlavorWheel 조회 완료 - 요청: {}건, 조회성공: {}건", flavorWheelIdList.size(), flavorWheels.size());
		return flavorWheels;
	}

	@Override
	public List<FlavorWheel> findFlavorWheelsByReviewId(final Long reviewId) {
		if (reviewId == null) {
			throw new ReviewException(ErrorCode.INVALID_REVIEW_REQUEST);
		}
		List<FlavorWheel> flavorWheels = queryMapper.findAllByReviewId(reviewId);
		log.info("[Review] FlavorWheel 조회 완료 - review ID: {}, 조회성공: {}건", reviewId, flavorWheels.size());
		return flavorWheels;
	}
}
