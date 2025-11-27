package com.backend.domain.user.controller.command;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.response.BaseResponse;
import com.backend.common.util.ResponseUtils;
import com.backend.domain.user.dto.request.UserReqDto;
import com.backend.domain.user.dto.response.UserResDto;
import com.backend.domain.user.service.command.UserCommandService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCommandController {
	private final UserCommandService service;

	@PostMapping
	public ResponseEntity<BaseResponse<UserResDto>> findUserById(final UserReqDto reqDto) {
		UserResDto response = service.insert(reqDto);
		return ResponseUtils.created(response);
	}
}
