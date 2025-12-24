package com.backend.domain.preference.mapper.command;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.backend.domain.preference.entity.UserPreference;

@Mapper
public interface PreferenceCommandMapper {

	int insert(@Param("preference") UserPreference preference);

	int update(@Param("preference") UserPreference preference);

	int deleteByUserId(@Param("userId") Long userId);
}
