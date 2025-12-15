package com.backend.domain.visit.mapper.query;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import com.backend.domain.visit.entity.Visit;

@Mapper
public interface VisitQueryMapper {
	Optional<Visit> findById(@Param("visitId") Long visitId);

	List<Visit> findAllByUserId(@Param("userId") Long userId, @Param("pageable") Pageable pageable);

	int countAllByUserId(@Param("userId") Long userId);
}
