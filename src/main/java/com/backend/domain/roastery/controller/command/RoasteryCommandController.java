package com.backend.domain.roastery.controller.command;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.annotation.CurrentUser;
import com.backend.common.auth.principal.AuthenticatedUser;
import com.backend.common.response.BaseResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.roastery.dto.request.RoasteryCreateReqDto;
import com.backend.domain.roastery.dto.request.RoasteryUpdateReqDto;
import com.backend.domain.roastery.dto.response.RoasteryResDto;
import com.backend.domain.roastery.service.facade.RoasteryFacadeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Tag(name = "Roastery", description = "로스터리 관리 API")
@RestController
@RequestMapping("/roasteries")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class RoasteryCommandController {

	private final RoasteryFacadeService roasteryFacadeService;

	@Operation(
		summary = "로스터리 생성",
		description = "새로운 로스터리를 등록합니다. ROASTERY_MANAGER 권한이 필요합니다."
	)
	@PreAuthorize("hasRole('ROLE_ROASTERY_MANAGER')")
	@PostMapping
	public ResponseEntity<BaseResponse<RoasteryResDto>> createRoastery(
		@CurrentUser AuthenticatedUser token,
		@RequestBody @Valid RoasteryCreateReqDto reqDto
	) {
		return ResponseUtils.created(roasteryFacadeService.createRoastery(token.getUser().getId(), reqDto));
	}

	@Operation(
		summary = "로스터리 수정",
		description = "로스터리 정보를 수정합니다. 본인이 소유한 로스터리만 수정할 수 있습니다."
	)
	@PreAuthorize("hasRole('ROLE_ROASTERY_MANAGER')")
	@PatchMapping("/{roasteryId}")
	public ResponseEntity<BaseResponse<RoasteryResDto>> updateRoastery(
		@PathVariable Long roasteryId,
		@CurrentUser AuthenticatedUser token,
		@RequestBody @Valid RoasteryUpdateReqDto reqDto
	) {
		return ResponseUtils.ok(roasteryFacadeService.updateRoastery(roasteryId, token.getUser().getId(), reqDto));
	}
}
