package com.backend.domain.roastery.mapper.command;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.backend.domain.roastery.entity.Roastery;

@Mapper
public interface RoasteryCommandMapper {
	int insert(@Param("roastery") Roastery roastery);

	int update(@Param("roastery") Roastery roastery);
}
