package com.backend.domain.review.mapper.query;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;

import com.backend.domain.review.entity.Review;

@Mapper
public interface ReviewQueryMapper {
	Optional<Review> findById(Long reviewId);

	List<Review> findAllByUserId(Long userId, Pageable pageable);

	int countAllByUserId(Long userId);

	List<Review> findAllByStoreId(Long storeId, Pageable pageable);

	int countAllByStoreId(Long storeId);
}
