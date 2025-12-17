package com.backend.domain.menu.controller.query;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.response.BaseResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.menu.dto.response.MenuDetailResDto;
import com.backend.domain.menu.service.facade.MenuFacadeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Tag(name = "Menu", description = "메뉴 조회 API")
@RestController
@RequestMapping("/menus")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuQueryController {

	private final MenuFacadeService menuFacadeService;

	@Operation(
		summary = "메뉴 상세 조회",
		description = "메뉴의 상세 정보를 조회합니다. 연결된 원두 정보도 함께 반환됩니다."
	)
	@GetMapping("/{menuId}")
	public ResponseEntity<BaseResponse<MenuDetailResDto>> getMenuDetail(
		@PathVariable Long menuId
	) {
		return ResponseUtils.ok(menuFacadeService.getMenuDetail(menuId));
	}
}
