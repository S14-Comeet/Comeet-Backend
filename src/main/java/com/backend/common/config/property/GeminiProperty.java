package com.backend.common.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;

@Validated
@ConfigurationProperties(prefix = "gemini")
public record GeminiProperty(
	@NotBlank
	String apiKey,
	@NotBlank
	String model
) {
}
