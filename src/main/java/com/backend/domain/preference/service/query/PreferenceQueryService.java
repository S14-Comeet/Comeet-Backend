package com.backend.domain.preference.service.query;

import java.util.Optional;

import com.backend.domain.preference.entity.UserPreference;

/**
 * UserPreference Query Service Interface
 */
public interface PreferenceQueryService {

	/**
	 * userId로 취향 조회
	 */
	Optional<UserPreference> findByUserId(Long userId);

	/**
	 * userId로 취향 조회 (없으면 예외)
	 */
	UserPreference getByUserId(Long userId);

	/**
	 * userId로 취향 존재 여부 확인
	 */
	boolean existsByUserId(Long userId);
}
