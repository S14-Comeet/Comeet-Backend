package com.backend.domain.review.service.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.domain.review.mapper.command.ReviewCommandMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewCommandServiceImpl implements ReviewCommandService {
	private final ReviewCommandMapper commandMapper;
}
