package com.backend.domain.bookmark.mapper.command;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.backend.domain.bookmark.entity.BookmarkFolder;
import com.backend.domain.bookmark.entity.BookmarkItem;

@Mapper
public interface BookmarkCommandMapper {

	void saveFolder(@Param("folder") BookmarkFolder folder);

	int updateFolder(@Param("folder") BookmarkFolder folder);

	int deleteFolder(@Param("folderId") Long folderId);

	void saveItem(@Param("item") BookmarkItem item);

	int deleteItem(
		@Param("folderId") Long folderId,
		@Param("storeId") Long storeId
	);
}
