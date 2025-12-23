package com.backend.domain.bookmark.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "폴더 내 카페 목록 응답 DTO")
public record FolderStoresResDto(
	@Schema(description = "폴더 정보")
	FolderSummary folder,

	@Schema(description = "저장된 카페 목록")
	List<BookmarkedStoreResDto> stores
) {
	@Builder
	@Schema(description = "폴더 요약 정보")
	public record FolderSummary(
		@Schema(description = "폴더 ID", example = "1")
		Long id,

		@Schema(description = "폴더 이름", example = "단골 카페")
		String name
	) {
	}
}
