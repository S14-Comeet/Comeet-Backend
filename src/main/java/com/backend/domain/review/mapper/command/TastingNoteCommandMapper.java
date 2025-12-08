package com.backend.domain.review.mapper.command;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TastingNoteCommandMapper {
	int insertTastingNotes(Long reviewId, List<Long> flavorWheelIdList);
}