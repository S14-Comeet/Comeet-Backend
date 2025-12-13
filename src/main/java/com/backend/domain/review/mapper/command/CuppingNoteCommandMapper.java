package com.backend.domain.review.mapper.command;

import org.apache.ibatis.annotations.Mapper;

import com.backend.domain.review.entity.CuppingNote;

@Mapper
public interface CuppingNoteCommandMapper {
	int insert(CuppingNote cuppingNote);

	int update(CuppingNote cuppingNote);
}
