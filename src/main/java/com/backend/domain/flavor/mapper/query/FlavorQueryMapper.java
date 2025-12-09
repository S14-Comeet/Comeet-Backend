package com.backend.domain.flavor.mapper.query;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.backend.domain.flavor.entity.Flavor;

@Mapper
public interface FlavorQueryMapper {
	List<Flavor> findAllByIds(List<Long> flavorIdList);

	List<Flavor> findAllByReviewId(Long reviewId);
}
