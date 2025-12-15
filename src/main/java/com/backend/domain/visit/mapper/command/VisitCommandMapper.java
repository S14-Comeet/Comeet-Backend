package com.backend.domain.visit.mapper.command;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.backend.domain.visit.entity.Visit;

@Mapper
public interface VisitCommandMapper {
	int save(@Param("visit") Visit visit);
}
