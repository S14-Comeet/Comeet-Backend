package com.backend.domain.preference.dto.request;

import java.util.List;

import com.backend.domain.bean.enums.RoastingLevel;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Schema(description = "사용자 취향 업데이트 요청")
public record PreferenceUpdateReqDto(
	@Schema(description = "선호 산미 (1-10)", example = "7") @Min(1) @Max(10) Integer prefAcidity,

	@Schema(description = "선호 바디감 (1-10)", example = "5") @Min(1) @Max(10) Integer prefBody,

	@Schema(description = "선호 단맛 (1-10)", example = "6") @Min(1) @Max(10) Integer prefSweetness,

	@Schema(description = "선호 쓴맛 (1-10)", example = "4") @Min(1) @Max(10) Integer prefBitterness,

	@Schema(description = "선호 로스팅 레벨", example = "[\"LIGHT\", \"MEDIUM\"]") List<RoastingLevel> preferredRoastLevels,

	@Schema(description = "선호 플레이버 태그", example = "[\"BLACKBERRY\", \"ROSE\", \"CHOCOLATE\"]") List<String> likedTags,

	@Schema(description = "비선호 플레이버 태그", example = "[\"SMOKY\", \"BURNT\"]") List<String> dislikedTags) {
}
