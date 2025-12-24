package com.backend.domain.preference.service.command;

import com.backend.domain.preference.entity.UserPreference;

public interface PreferenceCommandService {

	void save(UserPreference preference);

	void update(UserPreference preference);

	void deleteByUserId(Long userId);
}
