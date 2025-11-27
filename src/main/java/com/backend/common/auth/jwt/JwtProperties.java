package com.backend.common.auth.jwt;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
	@Valid @NotBlank String secret,
	@Valid @NotNull Long accessTokenExpiration,
	@Valid @NotNull Long refreshTokenExpiration
) {
}