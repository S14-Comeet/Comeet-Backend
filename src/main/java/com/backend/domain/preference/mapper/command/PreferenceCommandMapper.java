package com.backend.domain.preference.mapper.command;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.backend.domain.preference.entity.UserPreference;

/**
 * UserPreference Command Mapper
 */
@Mapper
public interface PreferenceCommandMapper {

	/**
	 * 사용자 취향 저장
	 */
	int insert(@Param("preference") UserPreference preference);

	/**
	 * 사용자 취향 업데이트
	 */
	int update(@Param("preference") UserPreference preference);

	/**
	 * 사용자 취향 삭제
	 */
	int deleteByUserId(@Param("userId") Long userId);
}
