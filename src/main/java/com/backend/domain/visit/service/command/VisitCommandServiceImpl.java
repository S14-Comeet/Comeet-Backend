package com.backend.domain.visit.service.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.domain.visit.entity.Visit;
import com.backend.domain.visit.mapper.command.VisitCommandMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class VisitCommandServiceImpl implements VisitCommandService {
	private final VisitCommandMapper commandMapper;

	@Override
	public int save(final Visit visit) {
		log.info("[Visit] 방문 인증 기록 저장");
		return commandMapper.save(visit);
	}
}
