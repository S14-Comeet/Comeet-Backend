package com.backend.domain.bean.controller.query;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.response.BaseResponse;
import com.backend.common.response.PageResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.bean.dto.response.BeanFlavorResDto;
import com.backend.domain.bean.dto.response.BeanResDto;
import com.backend.domain.bean.service.facade.BeanFacadeService;
import com.backend.domain.bean.service.query.BeanFlavorQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Tag(name = "Bean", description = "원두 관리 API")
@RestController
@RequestMapping("/beans")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class BeanQueryController {

	private final BeanFacadeService beanFacadeService;
	private final BeanFlavorQueryService beanFlavorQueryService;

	@Operation(
		summary = "원두 상세 조회",
		description = "원두 ID로 원두의 상세 정보를 조회합니다."
	)
	@GetMapping("/{beanId}")
	public ResponseEntity<BaseResponse<BeanResDto>> getBean(@PathVariable Long beanId) {
		return ResponseUtils.ok(beanFacadeService.getBean(beanId));
	}

	@Operation(
		summary = "모든 원두 목록 조회",
		description = "전체 원두 목록을 페이지네이션으로 조회합니다."
	)
	@GetMapping
	public ResponseEntity<PageResponse<BeanResDto>> getAllBeans(
		@Parameter(description = "페이지 번호 (1부터 시작)", example = "1")
		@RequestParam(defaultValue = "1") @Min(1) int page,
		@Parameter(description = "페이지 크기", example = "10")
		@RequestParam(defaultValue = "10") @Min(1) int size
	) {
		Page<BeanResDto> response = beanFacadeService.getAllBeans(page, size);
		return ResponseUtils.page(response);
	}

	@Operation(
		summary = "로스터리별 원두 목록 조회",
		description = "특정 로스터리의 원두 목록을 페이지네이션으로 조회합니다."
	)
	@GetMapping("/roastery/{roasteryId}")
	public ResponseEntity<PageResponse<BeanResDto>> getBeansByRoastery(
		@PathVariable Long roasteryId,
		@Parameter(description = "페이지 번호 (1부터 시작)", example = "1")
		@RequestParam(defaultValue = "1") @Min(1) int page,
		@Parameter(description = "페이지 크기", example = "10")
		@RequestParam(defaultValue = "10") @Min(1) int size
	) {
		Page<BeanResDto> response = beanFacadeService.getBeansByRoastery(roasteryId, page, size);
		return ResponseUtils.page(response);
	}

	@Operation(
		summary = "생산 국가로 원두 검색",
		description = "생산 국가 키워드로 원두를 검색하여 목록을 페이지네이션으로 조회합니다."
	)
	@GetMapping("/search")
	public ResponseEntity<PageResponse<BeanResDto>> searchBeansByCountry(
		@Parameter(description = "검색 키워드 (생산 국가)", example = "에티오피아")
		@RequestParam @NotBlank String keyword,
		@Parameter(description = "페이지 번호 (1부터 시작)", example = "1")
		@RequestParam(defaultValue = "1") @Min(1) int page,
		@Parameter(description = "페이지 크기", example = "10")
		@RequestParam(defaultValue = "10") @Min(1) int size
	) {
		Page<BeanResDto> response = beanFacadeService.searchBeansByCountry(keyword, page, size);
		return ResponseUtils.page(response);
	}

	@Operation(
		summary = "원두-플레이버 매핑 조회",
		description = "원두에 매핑된 플레이버 목록을 조회합니다."
	)
	@GetMapping("/{beanId}/flavors")
	public ResponseEntity<BaseResponse<BeanFlavorResDto>> getBeanFlavors(
		@PathVariable Long beanId
	) {
		return ResponseUtils.ok(beanFlavorQueryService.getBeanFlavors(beanId));
	}
}
