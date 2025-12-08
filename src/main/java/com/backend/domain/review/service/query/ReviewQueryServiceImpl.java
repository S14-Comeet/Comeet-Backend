package com.backend.domain.review.service.query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.domain.review.mapper.query.ReviewQueryMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewQueryServiceImpl implements ReviewQueryService {
	private final ReviewQueryMapper queryMapper;

}
