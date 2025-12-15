package com.backend.domain.review.mapper.command;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.backend.domain.review.entity.CuppingNote;

@Mapper
public interface CuppingNoteCommandMapper {
	int insert(@Param("cuppingNote") CuppingNote cuppingNote);

	int update(@Param("cuppingNote") CuppingNote cuppingNote);
}
