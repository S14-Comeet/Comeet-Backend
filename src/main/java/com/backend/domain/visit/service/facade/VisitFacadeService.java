package com.backend.domain.visit.service.facade;

import org.springframework.stereotype.Service;

import com.backend.domain.visit.service.command.VisitCommandService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class VisitFacadeService {
	private final VisitCommandService visitCommandService;

}
