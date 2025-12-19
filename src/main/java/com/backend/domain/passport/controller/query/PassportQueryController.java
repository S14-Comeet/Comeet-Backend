package com.backend.domain.passport.controller.query;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.annotation.CurrentUser;
import com.backend.common.auth.principal.AuthenticatedUser;
import com.backend.common.response.BaseResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.passport.dto.response.PassportDetailResDto;
import com.backend.domain.passport.dto.response.PassportListResDto;
import com.backend.domain.passport.service.facade.PassportFacadeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Tag(name = "Passport", description = "여권 관련 API")
@RestController
@RequestMapping("/passport")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PassportQueryController {

	private final PassportFacadeService passportFacadeService;

	@Operation(summary = "연도별 여권 목록 조회", description = "해당 연도의 1~12월 여권 목록을 조회합니다.")
	@GetMapping
	public ResponseEntity<BaseResponse<PassportListResDto>> getPassportList(
		@CurrentUser AuthenticatedUser user,
		@Parameter(description = "조회 연도", example = "2025", required = true)
		@RequestParam @NotNull @Min(2020) @Max(2100) Integer year
	) {
		PassportListResDto response = passportFacadeService.getPassportList(user.getUser().getId(), year);
		return ResponseUtils.ok(response);
	}

	@Operation(summary = "여권 상세 조회", description = "특정 여권의 상세 정보를 조회합니다.")
	@GetMapping("/{passportId}")
	public ResponseEntity<BaseResponse<PassportDetailResDto>> getPassportDetail(
		@CurrentUser AuthenticatedUser user,
		@Parameter(description = "여권 ID", example = "1", required = true)
		@PathVariable @NotNull @Min(1) Long passportId
	) {
		PassportDetailResDto response = passportFacadeService.getPassportDetail(passportId, user.getUser().getId());
		return ResponseUtils.ok(response);
	}
}
