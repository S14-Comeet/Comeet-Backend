package com.backend.domain.review.mapper.query;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.backend.domain.review.dto.common.ReviewFlavorDto;

@Mapper
public interface TastingNoteQueryMapper {
	List<Long> findFlavorIdsByReviewId(@Param("reviewId") Long reviewId);

	List<ReviewFlavorDto> findFlavorIdsByReviewIds(@Param("reviewIds") List<Long> reviewIds);
}
