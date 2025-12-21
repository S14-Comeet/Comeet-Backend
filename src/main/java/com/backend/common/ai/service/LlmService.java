package com.backend.common.ai.service;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

import com.backend.common.ai.dto.RerankRequest;
import com.backend.common.ai.dto.RerankResponse;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * LLM 기반 리랭킹 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LlmService {

	private final OpenAiChatModel chatModel;
	private final Gson gson = new Gson();

	private static final String RERANK_PROMPT_TEMPLATE = """
		당신은 전문 커피 소믈리에입니다. 사용자의 취향과 후보 원두들을 분석하여
		가장 적합한 3개를 선정하고 추천 이유를 설명해주세요.

		## 사용자 취향
		- 선호 산미: %d/10
		- 선호 바디감: %d/10
		- 선호 단맛: %d/10
		- 선호 쓴맛: %d/10
		- 선호 배전도: %s
		- 선호 플레이버: %s

		## 후보 원두 목록
		%s

		## 지시사항
		1. 사용자 취향과 가장 잘 맞는 원두 3개를 선정하세요
		2. 각 원두에 대해 1문장의 추천 이유를 작성하세요
		3. 취향 점수와 플레이버 태그의 유사도를 모두 고려하세요
		4. 반드시 아래 JSON 형식으로만 응답하세요

		## 응답 형식 (JSON만 출력, 다른 텍스트 없이)
		{
		  "recommendations": [
		    {"beanId": 1, "rank": 1, "reason": "추천 이유..."},
		    {"beanId": 2, "rank": 2, "reason": "추천 이유..."},
		    {"beanId": 3, "rank": 3, "reason": "추천 이유..."}
		  ]
		}
		""";

	/**
	 * 후보 원두들을 리랭킹하여 Top 3 추천
	 *
	 * @param request 사용자 취향 및 후보 원두 정보
	 * @return 리랭킹된 Top 3 추천 결과
	 */
	public RerankResponse rerank(RerankRequest request) {
		log.debug("Reranking {} candidates for user preference", request.candidates().size());

		String candidatesJson = formatCandidates(request);
		String promptText = String.format(
			RERANK_PROMPT_TEMPLATE,
			request.userPreference().prefAcidity(),
			request.userPreference().prefBody(),
			request.userPreference().prefSweetness(),
			request.userPreference().prefBitterness(),
			String.join(", ", request.userPreference().preferredRoastLevels()),
			String.join(", ", request.userPreference().likedTags()),
			candidatesJson
		);

		Prompt prompt = new Prompt(
			new UserMessage(promptText),
			OpenAiChatOptions.builder()
				.withModel("gpt-4o")
				.withTemperature(0.3)
				.build()
		);

		String response = chatModel.call(prompt).getResult().getOutput().getContent();
		log.debug("LLM response: {}", response);

		return parseResponse(response);
	}

	private String formatCandidates(RerankRequest request) {
		StringBuilder sb = new StringBuilder();
		int index = 1;
		for (RerankRequest.CandidateInfo candidate : request.candidates()) {
			sb.append(String.format(
				"%d. [ID:%d] %s (%s, %s)\n   - 산미:%d, 바디:%d, 단맛:%d, 쓴맛:%d\n   - 플레이버: %s\n   - 유사도: %.2f\n\n",
				index++,
				candidate.beanId(),
				candidate.beanName(),
				candidate.roasteryName(),
				candidate.roastLevel(),
				candidate.acidity(),
				candidate.body(),
				candidate.sweetness(),
				candidate.bitterness(),
				String.join(", ", candidate.flavorTags()),
				candidate.similarityScore()
			));
		}
		return sb.toString();
	}

	private RerankResponse parseResponse(String response) {
		try {
			// JSON 부분만 추출 (```json 블록이 있을 경우 처리)
			String jsonStr = response;
			if (response.contains("```json")) {
				int start = response.indexOf("```json") + 7;
				int end = response.indexOf("```", start);
				jsonStr = response.substring(start, end).trim();
			} else if (response.contains("```")) {
				int start = response.indexOf("```") + 3;
				int end = response.indexOf("```", start);
				jsonStr = response.substring(start, end).trim();
			}

			return gson.fromJson(jsonStr, RerankResponse.class);
		} catch (JsonSyntaxException e) {
			log.error("Failed to parse LLM response: {}", response, e);
			throw new RuntimeException("LLM 응답 파싱 실패", e);
		}
	}
}
