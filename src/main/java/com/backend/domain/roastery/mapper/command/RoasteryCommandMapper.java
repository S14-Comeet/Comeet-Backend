package com.backend.domain.roastery.mapper.command;

import org.apache.ibatis.annotations.Mapper;

import com.backend.domain.roastery.entity.Roastery;

@Mapper
public interface RoasteryCommandMapper {
	int insert(Roastery roastery);

	int update(Roastery roastery);
}
