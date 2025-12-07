package com.backend.domain.visit.controller.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.annotation.CurrentUser;
import com.backend.common.auth.principal.AuthenticatedUser;
import com.backend.common.response.BaseResponse;
import com.backend.common.response.PageResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.visit.dto.common.VisitInfoDto;
import com.backend.domain.visit.dto.common.VisitPageDto;
import com.backend.domain.visit.service.facade.VisitFacadeService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Tag(name = "방문 인증 API", description = "메뉴 기반 방문 인증 내역을 관리하는 API")
@RestController
@RequestMapping("/visit")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class VisitQueryController {

	private final VisitFacadeService visitFacadeService;

	@GetMapping("/history")
	public ResponseEntity<PageResponse<VisitPageDto>> getVisitHistory(
		@CurrentUser AuthenticatedUser token,
		@PageableDefault Pageable pageable
	) {
		Page<VisitPageDto> response = visitFacadeService.findAllWithPageableUserId(token.getUser(), pageable);
		return ResponseUtils.page(response);
	}

	@GetMapping("/{visitId}")
	public ResponseEntity<BaseResponse<VisitInfoDto>> getVisitById(
		@CurrentUser AuthenticatedUser token,
		@PathVariable Long visitId
	) {
		VisitInfoDto response = visitFacadeService.findVisitById(token.getUser(), visitId);
		return ResponseUtils.ok(response);
	}
}

