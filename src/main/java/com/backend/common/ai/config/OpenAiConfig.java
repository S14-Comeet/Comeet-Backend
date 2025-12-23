package com.backend.common.ai.config;

import java.io.IOException;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.Resource;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import lombok.extern.slf4j.Slf4j;

/**
 * OpenAI API 설정 (GMS 프록시 + Spring AI)
 */
@Slf4j
@Configuration
public class OpenAiConfig {

	@Value("classpath:/prompts/rerank_system_prompt.txt")
	private Resource rerankSystemPrompt;

	/**
	 * RestClient.Builder 빈 설정
	 * GMS 프록시는 chunked transfer encoding을 지원하지 않으므로 BufferingClientHttpRequestFactory 필요
	 */
	@Bean
	RestClient.Builder restClientBuilder() {
		return RestClient.builder()
			.requestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
	}

	/**
	 * ChatClient 빈 설정
	 */
	@Bean
	ChatClient openAiChatClient(@Qualifier("openAiChatModel") ChatModel chatModel) throws IOException {
		var loggerAdvisor = SimpleLoggerAdvisor.builder()
			.order(Ordered.LOWEST_PRECEDENCE - 1)
			.build();

		return ChatClient.builder(chatModel)
			.defaultSystem(rerankSystemPrompt)
			.defaultOptions(OpenAiChatOptions.builder()
				.maxCompletionTokens(4096)
				.build())
			.defaultAdvisors(loggerAdvisor)
			.build();
	}
}
