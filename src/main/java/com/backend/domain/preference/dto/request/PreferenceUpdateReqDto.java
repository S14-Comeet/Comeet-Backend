package com.backend.domain.preference.dto.request;

import java.util.List;

import com.backend.domain.bean.enums.RoastingLevel;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * 사용자 취향 업데이트 요청 DTO
 */
public record PreferenceUpdateReqDto(
	@Min(1) @Max(10)
	Integer prefAcidity,

	@Min(1) @Max(10)
	Integer prefBody,

	@Min(1) @Max(10)
	Integer prefSweetness,

	@Min(1) @Max(10)
	Integer prefBitterness,

	List<RoastingLevel> preferredRoastLevels,

	List<String> likedTags,

	List<String> dislikedTags
) {
}
