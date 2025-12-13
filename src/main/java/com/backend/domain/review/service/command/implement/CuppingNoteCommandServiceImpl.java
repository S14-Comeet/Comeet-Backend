package com.backend.domain.review.service.command.implement;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.domain.review.entity.CuppingNote;
import com.backend.domain.review.mapper.command.CuppingNoteCommandMapper;
import com.backend.domain.review.service.command.CuppingNoteCommandService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CuppingNoteCommandServiceImpl implements CuppingNoteCommandService {
	private final CuppingNoteCommandMapper commandMapper;

	@Override
	public int insert(final CuppingNote cuppingNote) {
		int result = commandMapper.insert(cuppingNote);
		log.info("[CuppingNote] 커핑 노트 저장 완료 - id: {}", cuppingNote.getId());
		return result;
	}

	@Override
	public int update(final CuppingNote cuppingNote) {
		int result = commandMapper.update(cuppingNote);
		log.info("[CuppingNote] 커핑 노트 업데이트 완료 - id: {}", cuppingNote.getId());
		return result;
	}
}
