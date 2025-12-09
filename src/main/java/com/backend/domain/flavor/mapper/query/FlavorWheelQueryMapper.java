package com.backend.domain.flavor.mapper.query;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.backend.domain.flavor.entity.FlavorWheel;

@Mapper
public interface FlavorWheelQueryMapper {
	List<FlavorWheel> findAllByIds(List<Long> flavorWheelIdList);

	List<FlavorWheel> findAllByReviewId(Long reviewId);
}
