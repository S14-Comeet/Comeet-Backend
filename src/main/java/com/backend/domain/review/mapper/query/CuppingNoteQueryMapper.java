package com.backend.domain.review.mapper.query;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.backend.domain.review.entity.CuppingNote;

@Mapper
public interface CuppingNoteQueryMapper {
	Optional<CuppingNote> findByReviewId(Long reviewId);

	boolean existsByReviewId(Long reviewId);
}
