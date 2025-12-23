package com.backend.domain.bookmark.service.command;

import com.backend.domain.bookmark.dto.response.BookmarkItemResDto;
import com.backend.domain.bookmark.dto.response.FolderResDto;
import com.backend.domain.bookmark.entity.BookmarkFolder;

public interface BookmarkCommandService {

	BookmarkFolder createFolder(BookmarkFolder folder);

	BookmarkFolder updateFolder(BookmarkFolder folder);

	void deleteFolder(Long folderId);

	BookmarkItemResDto addStoreToFolder(Long folderId, Long storeId);

	void removeStoreFromFolder(Long folderId, Long storeId);
}
