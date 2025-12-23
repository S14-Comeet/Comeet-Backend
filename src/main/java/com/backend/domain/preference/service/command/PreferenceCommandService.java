package com.backend.domain.preference.service.command;

import com.backend.domain.preference.entity.UserPreference;

/**
 * UserPreference Command Service Interface
 */
public interface PreferenceCommandService {

	/**
	 * 사용자 취향 저장
	 */
	void save(UserPreference preference);

	/**
	 * 사용자 취향 업데이트
	 */
	void update(UserPreference preference);

	/**
	 * 사용자 취향 삭제
	 */
	void deleteByUserId(Long userId);
}
