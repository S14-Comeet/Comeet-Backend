package com.backend.domain.review.mapper.command;

import org.apache.ibatis.annotations.Mapper;

import com.backend.domain.review.entity.Review;

@Mapper
public interface ReviewCommandMapper {
	int insert(Review review);
}
