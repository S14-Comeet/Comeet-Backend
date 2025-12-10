package com.backend.domain.flavor.service.facade;

import java.util.List;

import org.springframework.stereotype.Component;

import com.backend.domain.flavor.converter.FlavorConverter;
import com.backend.domain.flavor.dto.common.FlavorInfoDto;
import com.backend.domain.flavor.service.FlavorQueryService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class FlavorFacadeService {
	private final FlavorQueryService flavorQueryService;

	public List<FlavorInfoDto> getAllFlavors() {
		return flavorQueryService.findAll().stream()
			.map(FlavorConverter::toFlavorInfoDto)
			.toList();
	}
}
