package com.backend.domain.review.dto.response;

import java.util.List;

import com.backend.domain.review.dto.common.FlavorWheelBadgeDto;
import com.backend.domain.review.dto.common.ReviewInfoDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "리뷰 작성/수정 응답 DTO")
public record ReviewedResDto(
	@Schema(description = "리뷰 상세 정보")
	ReviewInfoDto reviewInfo,

	@Schema(description = "FlavorWheel 뱃지 목록")
	List<FlavorWheelBadgeDto> flavorWheelBadges
) {
}
