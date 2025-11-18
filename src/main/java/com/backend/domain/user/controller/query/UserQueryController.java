package com.backend.domain.user.controller.query;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.response.BaseResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.user.dto.response.UserResDto;
import com.backend.domain.user.service.query.UserQueryService;

import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserQueryController {
	private final UserQueryService userQueryService;

	@GetMapping("/{id}")
	public ResponseEntity<BaseResponse<UserResDto>> findUserById(final @Min(1) @PathVariable Long id) {
		UserResDto response = userQueryService.findById(id);
				return ResponseUtils.ok(response);
	}
}
