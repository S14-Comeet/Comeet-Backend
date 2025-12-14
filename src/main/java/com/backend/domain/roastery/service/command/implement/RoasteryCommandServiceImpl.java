package com.backend.domain.roastery.service.command.implement;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.domain.roastery.entity.Roastery;
import com.backend.domain.roastery.mapper.command.RoasteryCommandMapper;
import com.backend.domain.roastery.service.command.RoasteryCommandService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class RoasteryCommandServiceImpl implements RoasteryCommandService {
	private final RoasteryCommandMapper roasteryCommandMapper;

	@Override
	public int insert(final Roastery roastery) {
		return roasteryCommandMapper.insert(roastery);
	}

	@Override
	public int update(final Roastery roastery) {
		return roasteryCommandMapper.update(roastery);
	}
}
