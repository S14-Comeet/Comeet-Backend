package com.backend.domain.review.mapper.query;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.backend.domain.review.entity.CuppingNote;

@Mapper
public interface CuppingNoteQueryMapper {
	Optional<CuppingNote> findByReviewId(@Param("reviewId") Long reviewId);

	boolean existsByReviewId(@Param("reviewId") Long reviewId);
}
