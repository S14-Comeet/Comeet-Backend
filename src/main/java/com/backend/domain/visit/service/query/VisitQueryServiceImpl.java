package com.backend.domain.visit.service.query;

import static lombok.AccessLevel.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.VisitException;
import com.backend.domain.visit.entity.Visit;
import com.backend.domain.visit.mapper.query.VisitQueryMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = PROTECTED)
public class VisitQueryServiceImpl implements VisitQueryService {
	private VisitQueryMapper queryMapper;

	@Override
	public Visit findById(final Long visitId) {
		Visit visit = queryMapper.findById(visitId)
			.orElseThrow(() -> new VisitException(ErrorCode.VISIT_NOT_FOUND));
		log.info("[Visit] 방문 기록 상세 조회 id : {}", visit.getId());
		return visit;
	}
}
