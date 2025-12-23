package com.backend.domain.bookmark.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "북마크 폴더 수정 요청 DTO")
public record FolderUpdateReqDto(
	@Schema(description = "폴더 아이콘명", example = "home-fill")
	@Size(max = 50, message = "아이콘 이름은 50자를 초과할 수 없습니다.")
	String icon,

	@Schema(description = "폴더 이름", example = "집 근처 카페")
	@NotBlank(message = "폴더 이름은 필수 입력값입니다.")
	@Size(min = 1, max = 50, message = "폴더 이름은 1-50자 이내여야 합니다.")
	String name,

	@Schema(description = "폴더 설명", example = "집에서 가까운 카페들")
	@Size(max = 255, message = "폴더 설명은 255자를 초과할 수 없습니다.")
	String description
) {
}
