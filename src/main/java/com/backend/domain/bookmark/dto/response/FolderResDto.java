package com.backend.domain.bookmark.dto.response;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "북마크 폴더 응답 DTO")
public record FolderResDto(
	@Schema(description = "폴더 고유 ID", example = "1")
	Long id,

	@Schema(description = "폴더 아이콘명", example = "coffee")
	String icon,

	@Schema(description = "폴더 이름", example = "단골 카페")
	String name,

	@Schema(description = "폴더 설명", example = "자주 가는 카페들")
	String description,

	@Schema(description = "폴더에 저장된 카페 수", example = "5")
	Integer storeCount,

	@Schema(description = "폴더 생성 일시")
	LocalDateTime createdAt,

	@Schema(description = "마지막으로 카페가 추가된 일시")
	LocalDateTime lastAddedAt
) {
}
