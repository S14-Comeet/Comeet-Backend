package com.backend.domain.visit.mapper.query;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;

import com.backend.domain.visit.entity.Visit;

@Mapper
public interface VisitQueryMapper {
	Optional<Visit> findById(Long visitId);

	List<Visit> findAllByUserId(Long userId, Pageable pageable);

	int countAllByUserId(Long userId);
}
