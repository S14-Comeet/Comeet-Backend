package com.backend.domain.store.controller.command;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.annotation.CurrentUser;
import com.backend.common.auth.principal.AuthenticatedUser;
import com.backend.common.response.BaseResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.menu.dto.request.MenuCreateReqDto;
import com.backend.domain.menu.dto.response.MenuResDto;
import com.backend.domain.store.service.facade.StoreFacadeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Tag(name = "Store", description = "가맹점 관리 API")
@RestController
@RequestMapping("/stores")
@PreAuthorize("hasRole('ROLE_STORE_MANAGER')")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
class StoreCommandController {

	private final StoreFacadeService storeFacadeService;

	@Operation(
		summary = "메뉴 추가",
		description = "특정 가맹점에 새로운 메뉴를 추가합니다."
	)
	@PostMapping("/{storeId}/menus")
	public ResponseEntity<BaseResponse<MenuResDto>> createMenu(
		@PathVariable Long storeId,
		@CurrentUser AuthenticatedUser user,
		@RequestBody @Valid MenuCreateReqDto reqDto
	) {
		return ResponseUtils.created(storeFacadeService.createMenuForStore(storeId, user.getUser().getId(), reqDto));
	}
}
