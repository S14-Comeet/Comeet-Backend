package com.backend.domain.visit.service.query;

import com.backend.domain.visit.entity.Visit;

public interface VisitQueryService {
	Visit findById(Long visitId);
}
