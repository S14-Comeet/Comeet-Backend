package com.backend.domain.bookmark.service.query;

import java.util.List;

import com.backend.domain.bookmark.dto.response.BookmarkStatusResDto;
import com.backend.domain.bookmark.dto.response.BookmarkedStoreResDto;
import com.backend.domain.bookmark.dto.response.FolderResDto;
import com.backend.domain.bookmark.entity.BookmarkFolder;

public interface BookmarkQueryService {

	List<FolderResDto> findFoldersByUserId(Long userId);

	BookmarkFolder findFolderById(Long folderId);

	List<BookmarkedStoreResDto> findStoresByFolderId(Long folderId);

	BookmarkStatusResDto getBookmarkStatus(Long userId, Long storeId);

	boolean existsItem(Long folderId, Long storeId);
}
