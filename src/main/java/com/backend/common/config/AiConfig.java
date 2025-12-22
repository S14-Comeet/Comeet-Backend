package com.backend.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.backend.common.config.property.GeminiProperty;
import com.google.genai.Client;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AiConfig {

	private final GeminiProperty geminiProperty;

	@Bean
	public Client geminiClient() {
		return Client.builder()
			.apiKey(geminiProperty.apiKey())
			.build();
	}
}
