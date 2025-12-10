package com.backend.domain.flavor.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.response.BaseResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.flavor.dto.common.FlavorInfoDto;
import com.backend.domain.flavor.service.facade.FlavorFacadeService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Tag(name = "Flavor", description = "Flavor Wheel 관련 API")
@RestController
@RequestMapping("/flavors")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class FlavorQueryController {
	private final FlavorFacadeService flavorFacadeService;

	@GetMapping
	public ResponseEntity<BaseResponse<List<FlavorInfoDto>>> getAllFlavors() {
		List<FlavorInfoDto> response = flavorFacadeService.getAllFlavors();
		return ResponseUtils.ok(response);
	}
}
