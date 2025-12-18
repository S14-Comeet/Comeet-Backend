package com.backend.domain.roastery.factory;

import org.springframework.stereotype.Component;

import com.backend.common.util.ObjectUtils;
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

	public Roastery create(final RoasteryCreateReqDto dto) {
		Roastery roastery = Roastery.builder()
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
			.name(ObjectUtils.getOrDefault(dto.name(), existingRoastery.getName()))
			.logoUrl(ObjectUtils.getOrDefault(dto.logoUrl(), existingRoastery.getLogoUrl()))
			.websiteUrl(ObjectUtils.getOrDefault(dto.websiteUrl(), existingRoastery.getWebsiteUrl()))
			.createdAt(existingRoastery.getCreatedAt())
			.build();

		roasteryValidator.validate(roastery);
		return roastery;
	}
}
