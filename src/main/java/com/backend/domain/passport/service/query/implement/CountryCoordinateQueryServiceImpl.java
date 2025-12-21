package com.backend.domain.passport.service.query.implement;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.domain.passport.entity.CountryCoordinate;
import com.backend.domain.passport.mapper.query.CountryCoordinateQueryMapper;
import com.backend.domain.passport.service.query.CountryCoordinateQueryService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CountryCoordinateQueryServiceImpl implements CountryCoordinateQueryService {
	private final CountryCoordinateQueryMapper mapper;

	@Override
	public List<CountryCoordinate> findAll() {
		List<CountryCoordinate> coordinateList = mapper.findAll();
		log.info("[CountryCoordinate] 모든 국가 좌표 조회, size : {}", coordinateList.size());
		return coordinateList;
	}
}
