package com.backend.domain.review.service.command.implement;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.backend.domain.review.mapper.command.TastingNoteCommandMapper;
import com.backend.domain.review.service.command.TastingNoteCommandService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class TastingNoteCommandServiceImpl implements TastingNoteCommandService {
	private final TastingNoteCommandMapper commandMapper;

	@Override
	public void appendTastingNotes(final Long reviewId, final List<Long> flavorWheelIdList) {
		if (CollectionUtils.isEmpty(flavorWheelIdList)) {
			log.warn("[TastingNote] 저장할 FlavorWheel 목록이 비어있습니다. reviewId: {}", reviewId);
			return;
		}
		log.info("[TastingNote] 테이스팅 노트 저장 - reviewId: {}, 개수: {}건", reviewId, flavorWheelIdList.size());
		commandMapper.insertTastingNotes(reviewId, flavorWheelIdList);
	}

	@Override
	public void overwriteTastingNotes(Long reviewId, List<Long> flavorWheelIdList) {
		commandMapper.deleteAllByReviewId(reviewId);
		if (CollectionUtils.isEmpty(flavorWheelIdList)) {
			log.info("[TastingNote] 초기화 완료 - reviewId: {}", reviewId);
			return;
		}
		commandMapper.insertTastingNotes(reviewId, flavorWheelIdList);
	}

}