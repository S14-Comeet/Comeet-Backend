package com.backend.domain.review.mapper.query;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.backend.domain.review.dto.common.ReviewFlavorDto;

@Mapper
public interface TastingNoteQueryMapper {
	List<Long> findFlavorIdsByReviewId(Long reviewId);

	List<ReviewFlavorDto> findFlavorIdsByReviewIds(List<Long> reviewIds);
}
