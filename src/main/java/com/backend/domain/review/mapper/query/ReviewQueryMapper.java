package com.backend.domain.review.mapper.query;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.backend.domain.review.entity.Review;

@Mapper
public interface ReviewQueryMapper {
	Optional<Review> findById(Long reviewId);
}
