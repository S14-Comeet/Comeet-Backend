package com.backend.domain.passport.service.query;

import java.util.List;

import com.backend.domain.passport.entity.CountryCoordinate;

public interface CountryCoordinateQueryService {
	List<CountryCoordinate> findAll();
}
