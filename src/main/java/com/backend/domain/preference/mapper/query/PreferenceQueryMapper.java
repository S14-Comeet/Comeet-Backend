package com.backend.domain.preference.mapper.query;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.backend.domain.preference.entity.UserPreference;

@Mapper
public interface PreferenceQueryMapper {

	Optional<UserPreference> findByUserId(@Param("userId") Long userId);

	boolean existsByUserId(@Param("userId") Long userId);
}
