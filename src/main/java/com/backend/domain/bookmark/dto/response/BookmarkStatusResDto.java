package com.backend.domain.bookmark.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "카페 북마크 상태 응답 DTO")
public record BookmarkStatusResDto(
	@Schema(description = "하나 이상의 폴더에 저장되어 있으면 true", example = "true")
	boolean isBookmarked,

	@Schema(description = "저장된 폴더 목록")
	List<BookmarkedFolderInfo> folders
) {
	@Builder
	@Schema(description = "북마크된 폴더 정보")
	public record BookmarkedFolderInfo(
		@Schema(description = "폴더 ID", example = "1")
		Long folderId,

		@Schema(description = "폴더 이름", example = "단골 카페")
		String folderName,

		@Schema(description = "추가된 일시")
		LocalDateTime addedAt
	) {
	}
}
