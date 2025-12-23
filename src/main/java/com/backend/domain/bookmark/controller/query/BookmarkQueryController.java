package com.backend.domain.bookmark.controller.query;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.annotation.CurrentUser;
import com.backend.common.auth.principal.AuthenticatedUser;
import com.backend.common.response.BaseResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.bookmark.converter.BookmarkConverter;
import com.backend.domain.bookmark.dto.response.BookmarkStatusResDto;
import com.backend.domain.bookmark.dto.response.BookmarkedStoreResDto;
import com.backend.domain.bookmark.dto.response.FolderResDto;
import com.backend.domain.bookmark.dto.response.FolderStoresResDto;
import com.backend.domain.bookmark.entity.BookmarkFolder;
import com.backend.domain.bookmark.service.query.BookmarkQueryService;
import com.backend.domain.bookmark.validator.BookmarkValidator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Tag(name = "Bookmark", description = "북마크 관련 API")
@RestController
@RequestMapping("/bookmarks")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
class BookmarkQueryController {

	private final BookmarkQueryService bookmarkQueryService;
	private final BookmarkValidator bookmarkValidator;

	@Operation(
		summary = "내 폴더 목록 조회",
		description = "사용자의 북마크 폴더 목록을 조회합니다."
	)
	@GetMapping("/folders")
	public ResponseEntity<BaseResponse<List<FolderResDto>>> getFolders(
		@CurrentUser AuthenticatedUser user
	) {
		List<FolderResDto> response = bookmarkQueryService.findFoldersByUserId(user.getUser().getId());
		return ResponseUtils.ok(response);
	}

	@Operation(
		summary = "폴더 내 카페 목록 조회",
		description = "특정 폴더에 저장된 카페 목록을 조회합니다."
	)
	@GetMapping("/folders/{folderId}/stores")
	public ResponseEntity<BaseResponse<FolderStoresResDto>> getFolderStores(
		@Parameter(description = "폴더 ID", example = "1")
		@PathVariable Long folderId,
		@CurrentUser AuthenticatedUser user
	) {
		BookmarkFolder folder = bookmarkQueryService.findFolderById(folderId);
		bookmarkValidator.validateFolderOwnership(folder, user.getUser().getId());

		List<BookmarkedStoreResDto> stores = bookmarkQueryService.findStoresByFolderId(folderId);
		FolderStoresResDto response = BookmarkConverter.toFolderStoresResDto(folder, stores);

		return ResponseUtils.ok(response);
	}

	@Operation(
		summary = "카페 북마크 상태 조회",
		description = "특정 카페가 사용자의 어느 폴더에 저장되어 있는지 조회합니다."
	)
	@GetMapping("/stores/{storeId}/status")
	public ResponseEntity<BaseResponse<BookmarkStatusResDto>> getBookmarkStatus(
		@Parameter(description = "카페 ID", example = "123")
		@PathVariable Long storeId,
		@CurrentUser AuthenticatedUser user
	) {
		BookmarkStatusResDto response = bookmarkQueryService.getBookmarkStatus(user.getUser().getId(), storeId);
		return ResponseUtils.ok(response);
	}
}
