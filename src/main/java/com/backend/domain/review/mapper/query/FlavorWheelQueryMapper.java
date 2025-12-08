package com.backend.domain.review.mapper.query;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.backend.domain.review.entity.FlavorWheel;

@Mapper
public interface FlavorWheelQueryMapper {
	List<FlavorWheel> findAllByIds(List<Long> flavorWheelIdList);
}
