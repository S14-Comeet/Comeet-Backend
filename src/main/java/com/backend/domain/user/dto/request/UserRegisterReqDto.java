package com.backend.domain.user.dto.request;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UserRegisterReqDto(
	@Schema(description = "사용자 닉네임", example = "김싸피")
	@NotBlank(message = "닉네임은 공백일 수 없습니다.")
	@Length(min = 1, max = 12)
	String nickname
) {
}