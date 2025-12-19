package com.backend.common.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;

@Validated
@ConfigurationProperties(prefix = "spring.cloud.aws.s3")
public record S3Property(
	@NotBlank
	String bucket
) {
}
