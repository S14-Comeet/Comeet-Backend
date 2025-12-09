package com.backend.domain.flavor.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.ReviewException;
import com.backend.domain.flavor.entity.Flavor;
import com.backend.domain.flavor.mapper.query.FlavorQueryMapper;

import io.jsonwebtoken.lang.Collections;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class FlavorQueryServiceImpl implements FlavorQueryService {
	private final FlavorQueryMapper queryMapper;

	@Override
	public List<Flavor> findAllByIds(final List<Long> flavorIdList) {
		if (Collections.isEmpty(flavorIdList)) {
			return List.of();
		}
		List<Flavor> flavors = queryMapper.findAllByIds(flavorIdList);
		log.info("[Review] Flavor 조회 완료 - 요청: {}건, 조회성공: {}건", flavorIdList.size(), flavors.size());
		return flavors;
	}

	@Override
	public List<Flavor> findFlavorsByReviewId(final Long reviewId) {
		if (reviewId == null) {
			throw new ReviewException(ErrorCode.INVALID_REVIEW_REQUEST);
		}
		List<Flavor> flavors = queryMapper.findAllByReviewId(reviewId);
		log.info("[Review] Flavor 조회 완료 - review ID: {}, 조회성공: {}건", reviewId, flavors.size());
		return flavors;
	}

	@Override
	public List<Flavor> findAll() {
		List<Flavor> flavors = queryMapper.findAll();
		log.info("[Review] 모든 Flavor 조회 완료 - 조회성공: {}건", flavors.size());
		return flavors;
	}
}
