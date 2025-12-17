package com.backend.domain.bean.controller.command;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.response.BaseResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.bean.dto.request.BeanCreateReqDto;
import com.backend.domain.bean.dto.request.BeanUpdateReqDto;
import com.backend.domain.bean.dto.response.BeanResDto;
import com.backend.domain.bean.service.facade.BeanFacadeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Tag(name = "Bean", description = "원두 관리 API")
@RestController
@RequestMapping("/beans")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class BeanCommandController {

	private final BeanFacadeService beanFacadeService;

	@Operation(
		summary = "원두 생성",
		description = "새로운 원두를 등록합니다. MANAGER 권한이 필요합니다."
	)
	@PreAuthorize("hasRole('ROLE_MANAGER')")
	@PostMapping
	public ResponseEntity<BaseResponse<BeanResDto>> createBean(
		@RequestBody @Valid BeanCreateReqDto reqDto
	) {
		return ResponseUtils.created(beanFacadeService.createBean(reqDto));
	}

	@Operation(
		summary = "원두 수정",
		description = "원두 정보를 수정합니다. MANAGER 권한이 필요합니다."
	)
	@PreAuthorize("hasRole('ROLE_MANAGER')")
	@PatchMapping("/{beanId}")
	public ResponseEntity<BaseResponse<BeanResDto>> updateBean(
		@PathVariable Long beanId,
		@RequestBody @Valid BeanUpdateReqDto reqDto
	) {
		return ResponseUtils.ok(beanFacadeService.updateBean(beanId, reqDto));
	}

	@Operation(
		summary = "원두 삭제",
		description = "원두를 삭제(Soft Delete)합니다. MANAGER 권한이 필요합니다."
	)
	@PreAuthorize("hasRole('ROLE_MANAGER')")
	@DeleteMapping("/{beanId}")
	public ResponseEntity<BaseResponse<Void>> deleteBean(
		@PathVariable Long beanId
	) {
		beanFacadeService.deleteBean(beanId);
		return ResponseUtils.noContent();
	}
}
