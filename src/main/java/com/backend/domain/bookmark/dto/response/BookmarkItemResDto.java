package com.backend.domain.bookmark.dto.response;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "북마크 아이템 추가 응답 DTO")
public record BookmarkItemResDto(
	@Schema(description = "폴더 ID", example = "1")
	Long folderId,

	@Schema(description = "카페 ID", example = "789")
	Long storeId,

	@Schema(description = "추가된 일시")
	LocalDateTime addedAt
) {
}
