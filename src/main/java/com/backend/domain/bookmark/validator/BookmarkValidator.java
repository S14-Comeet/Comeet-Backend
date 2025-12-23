package com.backend.domain.bookmark.validator;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.BookmarkException;
import com.backend.common.validator.Validator;
import com.backend.domain.bookmark.entity.BookmarkFolder;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookmarkValidator implements Validator<BookmarkFolder> {

	private static final Set<String> SUPPORTED_ICONS = Set.of(
		"bookmark-fill", "coffee", "coffee-to-go", "home-fill", "event", "filter",
		"search", "user-line", "map-line", "notice", "check", "plus"
	);

	@Override
	public void validate(final BookmarkFolder folder) {
		validateFolderExists(folder);
		validateName(folder.getName());
		validateIcon(folder.getIcon());
	}

	public void validateFolderExists(final BookmarkFolder folder) {
		if (folder == null) {
			throw new BookmarkException(ErrorCode.BOOKMARK_FOLDER_NOT_FOUND);
		}
	}

	public void validateFolderOwnership(final BookmarkFolder folder, final Long userId) {
		validateFolderExists(folder);
		if (!folder.getUserId().equals(userId)) {
			throw new BookmarkException(ErrorCode.BOOKMARK_FOLDER_ACCESS_DENIED);
		}
	}

	public void validateName(final String name) {
		if (name == null || name.isBlank()) {
			throw new BookmarkException(ErrorCode.BOOKMARK_FOLDER_NAME_REQUIRED);
		}
	}

	public void validateIcon(final String icon) {
		if (icon != null && !SUPPORTED_ICONS.contains(icon)) {
			throw new BookmarkException(ErrorCode.BOOKMARK_INVALID_ICON);
		}
	}

	public void validateItemNotExists(final boolean exists) {
		if (exists) {
			throw new BookmarkException(ErrorCode.BOOKMARK_ITEM_ALREADY_EXISTS);
		}
	}

	public void validateItemExists(final boolean exists) {
		if (!exists) {
			throw new BookmarkException(ErrorCode.BOOKMARK_ITEM_NOT_FOUND);
		}
	}
}
