package com.backend.domain.review.dto.response;

import java.util.List;

import com.backend.domain.review.dto.common.FlavorWheelBadgeDto;
import com.backend.domain.review.dto.common.ReviewInfoDto;

import lombok.Builder;

@Builder
public record ReviewedResDto(
	ReviewInfoDto reviewInfoDto,
	List<FlavorWheelBadgeDto> flavorWheelBadgeDto
) {
}
