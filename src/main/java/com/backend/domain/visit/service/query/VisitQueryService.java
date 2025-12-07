package com.backend.domain.visit.service.query;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.backend.domain.visit.entity.Visit;

public interface VisitQueryService {
	Visit findById(Long visitId);

	List<Visit> findAllByUserId(Long userId, Pageable pageable);

	int countAllByUserId(Long userId);
}
