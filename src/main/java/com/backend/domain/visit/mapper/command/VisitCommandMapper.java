package com.backend.domain.visit.mapper.command;

import org.apache.ibatis.annotations.Mapper;

import com.backend.domain.visit.entity.Visit;

@Mapper
public interface VisitCommandMapper {
	int save(Visit visit);
}
