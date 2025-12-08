package com.backend.domain.review.service.query.implement;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.domain.review.entity.FlavorWheel;
import com.backend.domain.review.mapper.query.FlavorWheelQueryMapper;
import com.backend.domain.review.service.query.FlavorWheelQueryService;

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
}
