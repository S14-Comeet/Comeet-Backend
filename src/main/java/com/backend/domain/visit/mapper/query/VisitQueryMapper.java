package com.backend.domain.visit.mapper.query;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.backend.domain.visit.entity.Visit;

@Mapper
public interface VisitQueryMapper {
	Optional<Visit> findById(Long visitId);
}
