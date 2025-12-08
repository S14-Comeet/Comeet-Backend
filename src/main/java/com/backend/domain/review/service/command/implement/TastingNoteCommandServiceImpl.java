package com.backend.domain.review.service.command.implement;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		commandMapper.insertTastingNotes(reviewId, flavorWheelIdList);
	}
}
