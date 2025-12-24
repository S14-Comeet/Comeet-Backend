package com.backend.domain.bookmark.converter;

import java.time.LocalDateTime;
import java.util.List;

import com.backend.domain.bookmark.dto.request.FolderCreateReqDto;
import com.backend.domain.bookmark.dto.request.FolderUpdateReqDto;
import com.backend.domain.bookmark.dto.response.BookmarkItemResDto;
import com.backend.domain.bookmark.dto.response.BookmarkStatusResDto;
import com.backend.domain.bookmark.dto.response.BookmarkedStoreResDto;
import com.backend.domain.bookmark.dto.response.FolderResDto;
import com.backend.domain.bookmark.dto.response.FolderStoresResDto;
import com.backend.domain.bookmark.entity.BookmarkFolder;
import com.backend.domain.bookmark.entity.BookmarkItem;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BookmarkConverter {

	public static BookmarkFolder toFolderEntity(final FolderCreateReqDto reqDto, final Long userId) {
		return BookmarkFolder.builder()
			.userId(userId)
			.icon(reqDto.icon())
			.name(reqDto.name())
			.description(reqDto.description())
			.build();
	}

	public static BookmarkFolder toUpdatedFolderEntity(
		final BookmarkFolder existingFolder,
		final FolderUpdateReqDto reqDto
	) {
		return existingFolder.toBuilder()
			.icon(reqDto.icon() != null ? reqDto.icon() : existingFolder.getIcon())
			.name(reqDto.name())
			.description(reqDto.description())
			.build();
	}

	public static FolderResDto toFolderResDto(final BookmarkFolder folder) {
		return FolderResDto.builder()
			.id(folder.getId())
			.icon(folder.getIcon())
			.name(folder.getName())
			.description(folder.getDescription())
			.storeCount(0)
			.createdAt(folder.getCreatedAt())
			.lastAddedAt(null)
			.build();
	}

	public static BookmarkItem toItemEntity(final Long folderId, final Long storeId) {
		return BookmarkItem.builder()
			.folderId(folderId)
			.storeId(storeId)
			.build();
	}

	public static BookmarkItemResDto toItemResDto(
		final Long folderId,
		final Long storeId,
		final LocalDateTime addedAt
	) {
		return BookmarkItemResDto.builder()
			.folderId(folderId)
			.storeId(storeId)
			.addedAt(addedAt)
			.build();
	}

	public static FolderStoresResDto toFolderStoresResDto(
		final BookmarkFolder folder,
		final List<BookmarkedStoreResDto> stores
	) {
		return FolderStoresResDto.builder()
			.folder(FolderStoresResDto.FolderSummary.builder()
				.id(folder.getId())
				.name(folder.getName())
				.build())
			.stores(stores)
			.build();
	}

	public static BookmarkStatusResDto toBookmarkStatusResDto(
		final List<BookmarkStatusResDto.BookmarkedFolderInfo> folders
	) {
		return BookmarkStatusResDto.builder()
			.isBookmarked(!folders.isEmpty())
			.folders(folders)
			.build();
	}
}
