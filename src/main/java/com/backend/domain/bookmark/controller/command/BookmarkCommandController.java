package com.backend.domain.bookmark.controller.command;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.annotation.CurrentUser;
import com.backend.common.auth.principal.AuthenticatedUser;
import com.backend.common.response.BaseResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.bookmark.converter.BookmarkConverter;
import com.backend.domain.bookmark.dto.request.FolderCreateReqDto;
import com.backend.domain.bookmark.dto.request.FolderUpdateReqDto;
import com.backend.domain.bookmark.dto.response.BookmarkItemResDto;
import com.backend.domain.bookmark.dto.response.BookmarkedStoreResDto;
import com.backend.domain.bookmark.dto.response.FolderResDto;
import com.backend.domain.bookmark.dto.response.FolderStoresResDto;
import com.backend.domain.bookmark.entity.BookmarkFolder;
import com.backend.domain.bookmark.service.command.BookmarkCommandService;
import com.backend.domain.bookmark.service.query.BookmarkQueryService;
import com.backend.domain.bookmark.validator.BookmarkValidator;
import com.backend.domain.store.service.query.StoreQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Tag(name = "Bookmark", description = "북마크 관련 API")
@RestController
@RequestMapping("/bookmarks")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
class BookmarkCommandController {

	private final BookmarkCommandService bookmarkCommandService;
	private final BookmarkQueryService bookmarkQueryService;
	private final BookmarkValidator bookmarkValidator;
	private final StoreQueryService storeQueryService;

	@Operation(
		summary = "폴더 생성",
		description = "새 북마크 폴더를 생성합니다."
	)
	@PostMapping("/folders")
	public ResponseEntity<BaseResponse<FolderResDto>> createFolder(
		@RequestBody @Valid FolderCreateReqDto reqDto,
		@CurrentUser AuthenticatedUser user
	) {
		bookmarkValidator.validateIcon(reqDto.icon());

		BookmarkFolder folder = BookmarkConverter.toFolderEntity(reqDto, user.getUser().getId());
		BookmarkFolder savedFolder = bookmarkCommandService.createFolder(folder);
		FolderResDto response = BookmarkConverter.toFolderResDto(savedFolder);

		return ResponseUtils.created(response);
	}

	@Operation(
		summary = "폴더 수정",
		description = "기존 북마크 폴더의 메타데이터를 수정합니다."
	)
	@PutMapping("/folders/{folderId}")
	public ResponseEntity<BaseResponse<FolderResDto>> updateFolder(
		@Parameter(description = "폴더 ID", example = "1")
		@PathVariable Long folderId,
		@RequestBody @Valid FolderUpdateReqDto reqDto,
		@CurrentUser AuthenticatedUser user
	) {
		BookmarkFolder existingFolder = bookmarkQueryService.findFolderById(folderId);
		bookmarkValidator.validateFolderOwnership(existingFolder, user.getUser().getId());
		bookmarkValidator.validateIcon(reqDto.icon());

		BookmarkFolder updatedFolder = BookmarkConverter.toUpdatedFolderEntity(existingFolder, reqDto);
		bookmarkCommandService.updateFolder(updatedFolder);

		List<BookmarkedStoreResDto> stores = bookmarkQueryService.findStoresByFolderId(folderId);
		FolderStoresResDto folderWithStores = BookmarkConverter.toFolderStoresResDto(updatedFolder, stores);

		FolderResDto response = FolderResDto.builder()
			.id(updatedFolder.getId())
			.icon(updatedFolder.getIcon())
			.name(updatedFolder.getName())
			.description(updatedFolder.getDescription())
			.storeCount(stores.size())
			.createdAt(updatedFolder.getCreatedAt())
			.lastAddedAt(stores.isEmpty() ? null : stores.get(0).addedAt())
			.build();

		return ResponseUtils.ok(response);
	}

	@Operation(
		summary = "폴더 삭제",
		description = "북마크 폴더를 삭제합니다. 폴더 내 모든 북마크 아이템도 함께 삭제됩니다."
	)
	@DeleteMapping("/folders/{folderId}")
	public ResponseEntity<BaseResponse<Void>> deleteFolder(
		@Parameter(description = "폴더 ID", example = "1")
		@PathVariable Long folderId,
		@CurrentUser AuthenticatedUser user
	) {
		BookmarkFolder folder = bookmarkQueryService.findFolderById(folderId);
		bookmarkValidator.validateFolderOwnership(folder, user.getUser().getId());

		bookmarkCommandService.deleteFolder(folderId);
		return ResponseUtils.noContent();
	}

	@Operation(
		summary = "카페를 폴더에 추가",
		description = "카페를 특정 폴더에 북마크합니다."
	)
	@PostMapping("/folders/{folderId}/stores/{storeId}")
	public ResponseEntity<BaseResponse<BookmarkItemResDto>> addStoreToFolder(
		@Parameter(description = "폴더 ID", example = "1")
		@PathVariable Long folderId,
		@Parameter(description = "카페 ID", example = "789")
		@PathVariable Long storeId,
		@CurrentUser AuthenticatedUser user
	) {
		BookmarkFolder folder = bookmarkQueryService.findFolderById(folderId);
		bookmarkValidator.validateFolderOwnership(folder, user.getUser().getId());

		storeQueryService.findById(storeId);

		boolean exists = bookmarkQueryService.existsItem(folderId, storeId);
		bookmarkValidator.validateItemNotExists(exists);

		BookmarkItemResDto response = bookmarkCommandService.addStoreToFolder(folderId, storeId);
		return ResponseUtils.created(response);
	}

	@Operation(
		summary = "카페를 폴더에서 제거",
		description = "카페를 폴더에서 북마크 해제합니다."
	)
	@DeleteMapping("/folders/{folderId}/stores/{storeId}")
	public ResponseEntity<BaseResponse<Void>> removeStoreFromFolder(
		@Parameter(description = "폴더 ID", example = "1")
		@PathVariable Long folderId,
		@Parameter(description = "카페 ID", example = "789")
		@PathVariable Long storeId,
		@CurrentUser AuthenticatedUser user
	) {
		BookmarkFolder folder = bookmarkQueryService.findFolderById(folderId);
		bookmarkValidator.validateFolderOwnership(folder, user.getUser().getId());

		boolean exists = bookmarkQueryService.existsItem(folderId, storeId);
		bookmarkValidator.validateItemExists(exists);

		bookmarkCommandService.removeStoreFromFolder(folderId, storeId);
		return ResponseUtils.noContent();
	}
}
