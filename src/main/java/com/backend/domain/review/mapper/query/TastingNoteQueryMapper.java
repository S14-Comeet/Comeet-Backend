package com.backend.domain.review.mapper.query;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.backend.domain.review.dto.common.ReviewFlavorWheelDto;

@Mapper
public interface TastingNoteQueryMapper {
	List<Long> findFlavorWheelIdsByReviewId(Long reviewId);

	List<ReviewFlavorWheelDto> findFlavorWheelIdsByReviewIds(List<Long> reviewIds);
}
