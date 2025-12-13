package com.backend.domain.review.service.query.implement;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.ReviewException;
import com.backend.domain.review.entity.CuppingNote;
import com.backend.domain.review.mapper.query.CuppingNoteQueryMapper;
import com.backend.domain.review.service.query.CuppingNoteQueryService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CuppingNoteQueryServiceImpl implements CuppingNoteQueryService {
	private final CuppingNoteQueryMapper queryMapper;

	@Override
	public CuppingNote findByReviewId(final Long reviewId) {
		CuppingNote cuppingNote = queryMapper.findByReviewId(reviewId)
			.orElseThrow(() -> new ReviewException(ErrorCode.CUPPING_NOTE_NOT_FOUND));
		log.info("[Review] CuffingNote 조회 완료 - ID: {}", cuppingNote.getId());
		return cuppingNote;
	}

	@Override
	public boolean existsByReviewId(final Long reviewId) {
		return queryMapper.existsByReviewId(reviewId);
	}
}
