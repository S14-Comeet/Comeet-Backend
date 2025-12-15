package com.backend.domain.roastery.service.command;

import com.backend.domain.roastery.entity.Roastery;

public interface RoasteryCommandService {
	int insert(Roastery roastery);

	int update(Roastery roastery);
}
