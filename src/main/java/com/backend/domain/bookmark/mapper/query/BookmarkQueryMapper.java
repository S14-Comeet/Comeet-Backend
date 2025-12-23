package com.backend.domain.bookmark.mapper.query;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.backend.domain.bookmark.dto.response.BookmarkStatusResDto;
import com.backend.domain.bookmark.dto.response.BookmarkedStoreResDto;
import com.backend.domain.bookmark.dto.response.FolderResDto;
import com.backend.domain.bookmark.entity.BookmarkFolder;
import com.backend.domain.bookmark.entity.BookmarkItem;

@Mapper
public interface BookmarkQueryMapper {

	List<FolderResDto> findFoldersByUserId(@Param("userId") Long userId);

	Optional<BookmarkFolder> findFolderById(@Param("folderId") Long folderId);

	List<BookmarkedStoreResDto> findStoresByFolderId(@Param("folderId") Long folderId);

	Optional<BookmarkItem> findItemByFolderIdAndStoreId(
		@Param("folderId") Long folderId,
		@Param("storeId") Long storeId
	);

	List<BookmarkStatusResDto.BookmarkedFolderInfo> findFoldersByUserIdAndStoreId(
		@Param("userId") Long userId,
		@Param("storeId") Long storeId
	);

	boolean existsItemByFolderIdAndStoreId(
		@Param("folderId") Long folderId,
		@Param("storeId") Long storeId
	);
}
