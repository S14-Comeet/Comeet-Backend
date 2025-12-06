package com.backend.domain.visit.controller.command;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.annotation.CurrentUser;
import com.backend.common.auth.principal.AuthenticatedUser;
import com.backend.common.response.BaseResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.visit.dto.request.VerifyReqDto;
import com.backend.domain.visit.dto.response.VerifiedResDto;
import com.backend.domain.visit.service.facade.VisitFacadeService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Tag(name = "방문 인증 API", description = "메뉴 기반 방문 인증 내역을 관리하는 API")
@RestController
@RequestMapping("/visit")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class VisitCommandController {

	private final VisitFacadeService visitFacadeService;

	@PostMapping("/verify")
	public ResponseEntity<BaseResponse<VerifiedResDto>> verifyStoreVisit(
		@CurrentUser AuthenticatedUser token,
		@RequestBody VerifyReqDto reqDto
	) {
		//TODO 방문 인증 서비스 로직 완성
		return ResponseUtils.noContent();
	}


}
