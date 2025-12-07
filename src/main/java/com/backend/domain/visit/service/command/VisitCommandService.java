package com.backend.domain.visit.service.command;

import com.backend.domain.visit.entity.Visit;

public interface VisitCommandService {
	int save(Visit visit);
}
