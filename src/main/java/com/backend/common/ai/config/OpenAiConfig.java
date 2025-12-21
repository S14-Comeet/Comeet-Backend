package com.backend.common.ai.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAI API 설정
 */
@Configuration
public class OpenAiConfig {

	@Value("${spring.ai.openai.api-key:}")
	private String apiKey;

	@Bean
	public OpenAiApi openAiApi() {
		return new OpenAiApi(apiKey);
	}

	@Bean
	public EmbeddingModel embeddingModel(OpenAiApi openAiApi) {
		return new OpenAiEmbeddingModel(openAiApi);
	}

	@Bean
	public OpenAiChatModel chatModel(OpenAiApi openAiApi) {
		return new OpenAiChatModel(openAiApi);
	}
}
