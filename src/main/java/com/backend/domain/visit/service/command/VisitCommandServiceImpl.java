package com.backend.domain.visit.service.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.domain.visit.mapper.command.VisitCommandMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class VisitCommandServiceImpl implements VisitCommandService {
	private final VisitCommandMapper commandMapper;
}
