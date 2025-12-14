package com.backend.domain.roastery.factory;

import org.springframework.stereotype.Component;

import com.backend.domain.roastery.dto.request.RoasteryCreateReqDto;
import com.backend.domain.roastery.dto.request.RoasteryUpdateReqDto;
import com.backend.domain.roastery.entity.Roastery;
import com.backend.domain.roastery.validator.RoasteryValidator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class RoasteryFactory {
	private final RoasteryValidator roasteryValidator;

	public Roastery create(final Long ownerId, final RoasteryCreateReqDto dto) {
		Roastery roastery = Roastery.builder()
			.ownerId(ownerId)
			.name(dto.name())
			.logoUrl(dto.logoUrl())
			.websiteUrl(dto.websiteUrl())
			.build();

		roasteryValidator.validate(roastery);
		return roastery;
	}

	public Roastery createForUpdate(final Roastery existingRoastery, final RoasteryUpdateReqDto dto) {
		Roastery roastery = Roastery.builder()
			.id(existingRoastery.getId())
			.ownerId(existingRoastery.getOwnerId())
			.name(getOrDefault(dto.name(), existingRoastery.getName()))
			.logoUrl(getOrDefault(dto.logoUrl(), existingRoastery.getLogoUrl()))
			.websiteUrl(getOrDefault(dto.websiteUrl(), existingRoastery.getWebsiteUrl()))
			.createdAt(existingRoastery.getCreatedAt())
			.build();

		roasteryValidator.validate(roastery);
		return roastery;
	}

	/**
	 * null 병합 헬퍼 메서드 (DRY 원칙 적용)
	 * 새 값이 null이면 기존 값을 반환, 아니면 새 값 반환
	 */
	private <T> T getOrDefault(final T newValue, final T existingValue) {
		return newValue != null ? newValue : existingValue;
	}
}
