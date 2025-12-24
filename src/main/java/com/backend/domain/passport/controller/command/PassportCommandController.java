package com.backend.domain.passport.controller.command;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.response.BaseResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.passport.dto.request.PassportGenerateReqDto;
import com.backend.domain.passport.service.facade.PassportFacadeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Tag(name = "Passport", description = "여권 관련 API")
@RestController
@RequestMapping("/passport")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PassportCommandController {

	private final PassportFacadeService passportFacadeService;

	@Operation(summary = "여권 생성 테스트 API", description = "지정한 년/월에 대해 여권을 생성합니다. userId가 있으면 해당 유저만, 없으면 해당 월에 방문한 모든 유저에 대해 생성합니다.")
	@PostMapping("/test/generate")
	public ResponseEntity<BaseResponse<Void>> testGeneratePassport(
		@Valid @RequestBody PassportGenerateReqDto request
	) {
		passportFacadeService.generatePassportsForMonth(request.year(), request.month(), request.userId());
		return ResponseUtils.noContent();
	}
}
