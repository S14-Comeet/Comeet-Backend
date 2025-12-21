package com.backend.domain.passport.mapper.query;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.backend.domain.passport.entity.CountryCoordinate;

@Mapper
public interface CountryCoordinateQueryMapper {
	List<CountryCoordinate> findAll();
}
