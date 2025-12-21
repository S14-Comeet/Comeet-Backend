package com.backend.domain.preference.mapper.query;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.backend.domain.preference.entity.UserPreference;

/**
 * UserPreference Query Mapper
 */
@Mapper
public interface PreferenceQueryMapper {

	/**
	 * userId로 취향 조회
	 */
	Optional<UserPreference> findByUserId(@Param("userId") Long userId);

	/**
	 * userId로 취향 존재 여부 확인
	 */
	boolean existsByUserId(@Param("userId") Long userId);
}
