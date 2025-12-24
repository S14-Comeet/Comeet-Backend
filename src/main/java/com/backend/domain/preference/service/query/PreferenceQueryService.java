package com.backend.domain.preference.service.query;

import java.util.Optional;

import com.backend.domain.preference.entity.UserPreference;

public interface PreferenceQueryService {

	Optional<UserPreference> findByUserId(Long userId);

	UserPreference getByUserId(Long userId);

	boolean existsByUserId(Long userId);
}
