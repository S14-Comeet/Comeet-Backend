package com.backend.domain.flavor.mapper.query;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.backend.domain.flavor.entity.Flavor;

@Mapper
public interface FlavorQueryMapper {
	List<Flavor> findAllByIds(@Param("flavorIdList") List<Long> flavorIdList);

	List<Flavor> findAllByReviewId(@Param("reviewId") Long reviewId);

	List<Flavor> findAll();
}
