package com.backend.domain.passport.controller.query;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.annotation.CurrentUser;
import com.backend.common.auth.principal.AuthenticatedUser;
import com.backend.common.response.BaseResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.passport.dto.response.CountryStatisticsResDto;
import com.backend.domain.passport.dto.response.RoasteryStatisticsResDto;
import com.backend.domain.passport.service.facade.PassportFacadeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Tag(name = "Passport Statistics", description = "여권 통계 관련 API")
@RestController
@RequestMapping("/passport/statistics")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PassportStatisticsQueryController {

	private final PassportFacadeService passportFacadeService;

	@Operation(summary = "로스터리별 방문 통계 조회", description = "사용자의 전체 기간 로스터리별 방문 횟수를 조회합니다.")
	@GetMapping("/roastery")
	public ResponseEntity<BaseResponse<RoasteryStatisticsResDto>> getRoasteryStatistics(
		@CurrentUser AuthenticatedUser user
	) {
		RoasteryStatisticsResDto response = passportFacadeService.getRoasteryStatistics(user.getUser().getId());
		return ResponseUtils.ok(response);
	}

	@Operation(summary = "국가별 원두 통계 조회", description = "사용자의 전체 기간 국가별 원두 소비 횟수를 조회합니다.")
	@GetMapping("/country")
	public ResponseEntity<BaseResponse<CountryStatisticsResDto>> getCountryStatistics(
		@CurrentUser AuthenticatedUser user
	) {
		CountryStatisticsResDto response = passportFacadeService.getCountryStatistics(user.getUser().getId());
		return ResponseUtils.ok(response);
	}
}
