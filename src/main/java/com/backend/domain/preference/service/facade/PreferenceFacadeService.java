package com.backend.domain.preference.service.facade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.PreferenceException;
import com.backend.domain.preference.converter.PreferenceConverter;
import com.backend.domain.preference.dto.request.PreferenceUpdateReqDto;
import com.backend.domain.preference.dto.response.PreferenceResDto;
import com.backend.domain.preference.entity.UserPreference;
import com.backend.domain.preference.service.command.PreferenceCommandService;
import com.backend.domain.preference.service.query.PreferenceQueryService;

import lombok.RequiredArgsConstructor;

/**
 * UserPreference Facade Service
 */
@Service
@RequiredArgsConstructor
public class PreferenceFacadeService {

	private final PreferenceCommandService preferenceCommandService;
	private final PreferenceQueryService preferenceQueryService;

	/**
	 * 사용자 취향 조회
	 */
	@Transactional(readOnly = true)
	public PreferenceResDto getPreference(Long userId) {
		UserPreference preference = preferenceQueryService.getByUserId(userId);
		return PreferenceConverter.toResDto(preference);
	}

	/**
	 * 사용자 취향 초기화 (없으면 생성)
	 */
	@Transactional
	public PreferenceResDto initPreference(Long userId) {
		if (preferenceQueryService.existsByUserId(userId)) {
			throw new PreferenceException(ErrorCode.PREFERENCE_ALREADY_EXISTS);
		}

		UserPreference preference = UserPreference.createDefault(userId);
		preferenceCommandService.save(preference);

		return PreferenceConverter.toResDto(preference);
	}

	/**
	 * 사용자 취향 업데이트
	 */
	@Transactional
	public PreferenceResDto updatePreference(Long userId, PreferenceUpdateReqDto reqDto) {
		UserPreference preference = preferenceQueryService.findByUserId(userId)
			.orElseGet(() -> {
				// 취향 정보가 없으면 기본값으로 생성
				UserPreference newPreference = UserPreference.createDefault(userId);
				preferenceCommandService.save(newPreference);
				return newPreference;
			});

		preference.update(
			reqDto.prefAcidity(),
			reqDto.prefBody(),
			reqDto.prefSweetness(),
			reqDto.prefBitterness(),
			reqDto.preferredRoastLevels(),
			reqDto.likedTags(),
			reqDto.dislikedTags()
		);

		preferenceCommandService.update(preference);

		return PreferenceConverter.toResDto(preference);
	}

	/**
	 * 사용자 취향 삭제
	 */
	@Transactional
	public void deletePreference(Long userId) {
		if (!preferenceQueryService.existsByUserId(userId)) {
			throw new PreferenceException(ErrorCode.PREFERENCE_NOT_FOUND);
		}
		preferenceCommandService.deleteByUserId(userId);
	}
}
