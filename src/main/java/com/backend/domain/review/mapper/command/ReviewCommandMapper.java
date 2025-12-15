package com.backend.domain.review.mapper.command;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.backend.domain.review.entity.Review;

@Mapper
public interface ReviewCommandMapper {
	int insert(@Param("review") Review review);

	int update(@Param("review") Review review);

	int softDelete(@Param("id") Long id);
}
