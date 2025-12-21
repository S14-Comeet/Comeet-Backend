package com.backend.domain.preference.service.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.domain.preference.entity.UserPreference;
import com.backend.domain.preference.mapper.command.PreferenceCommandMapper;

import lombok.RequiredArgsConstructor;

/**
 * UserPreference Command Service 구현체
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PreferenceCommandServiceImpl implements PreferenceCommandService {

	private final PreferenceCommandMapper preferenceCommandMapper;

	@Override
	public void save(UserPreference preference) {
		preferenceCommandMapper.insert(preference);
	}

	@Override
	public void update(UserPreference preference) {
		preferenceCommandMapper.update(preference);
	}

	@Override
	public void deleteByUserId(Long userId) {
		preferenceCommandMapper.deleteByUserId(userId);
	}
}
