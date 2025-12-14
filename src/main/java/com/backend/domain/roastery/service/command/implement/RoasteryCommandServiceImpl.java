package com.backend.domain.roastery.service.command.implement;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.domain.roastery.entity.Roastery;
import com.backend.domain.roastery.mapper.command.RoasteryCommandMapper;
import com.backend.domain.roastery.service.command.RoasteryCommandService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class RoasteryCommandServiceImpl implements RoasteryCommandService {
	private final RoasteryCommandMapper roasteryCommandMapper;

	@Override
	public int insert(final Roastery roastery) {
		int affectedRows = roasteryCommandMapper.insert(roastery);
		log.info("[Roastery] 로스터리 추가 - ID: {}", roastery.getId());
		return affectedRows;
	}

	@Override
	public int update(final Roastery roastery) {
		log.info("[Roastery] 로스터리 정보 업데이트 - ID: {}", roastery.getId());
		return roasteryCommandMapper.update(roastery);
	}
}
