package com.backend.common.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Service;

import com.backend.common.ai.dto.MenuRerankRequest;
import com.backend.common.ai.dto.MenuRerankResponse;
import com.backend.common.ai.dto.RerankRequest;
import com.backend.common.ai.dto.RerankResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * LLM 기반 리랭킹 서비스 (Spring AI + GMS 프록시)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LlmService {

	private final ChatClient openAiChatClient;

	private static final String RERANK_USER_PROMPT_TEMPLATE = """
		당신은 커피 탐험 앱 'Comeet'의 바리스타 가이드입니다.
		사용자가 새로운 커피를 발견하고 취향을 넓혀갈 수 있도록 친근하게 안내해주세요.

		## 사용자 취향 프로필
		- 선호 산미: %d/10
		- 선호 바디감: %d/10
		- 선호 단맛: %d/10
		- 선호 쓴맛: %d/10
		- 선호 배전도: %s
		- 좋아하는 플레이버: %s

		## 추천 후보 원두
		%s

		## 추천 이유 작성 가이드
		- 반말(~해요, ~거든요, ~이에요)로 친근하게 작성
		- 원두의 개성과 매력을 살려서 표현
		- 다양한 표현 사용 (맛 묘사, 어울리는 상황, 감성적 표현 등)
		- 1~2문장으로 간결하게

		좋은 예시:
		- "상큼한 베리향이 기분 좋게 퍼지는 원두예요. 아침에 마시면 하루가 산뜻해질 거예요!"
		- "고소한 초콜릿 풍미가 진하게 느껴져요. 달콤한 커피를 좋아한다면 딱이에요."
		- "묵직한 바디감에 은은한 견과류 향이 매력적이에요. 여유로운 오후에 어울려요."
		- "에티오피아 특유의 꽃향기가 인상적이에요. 새로운 맛을 탐험하고 싶다면 도전해보세요!"

		피해야 할 표현:
		- "~에 잘 맞습니다", "균형 잡혀 있습니다" 같은 딱딱한 표현
		- 모든 추천에 비슷한 패턴 반복

		## 지시사항
		1. 사용자 취향에 가장 잘 맞는 원두 5개를 선정
		2. 각 원두마다 개성 있는 추천 이유 작성 (서로 다른 스타일로)
		3. 반드시 아래 JSON 형식으로만 응답 (다른 텍스트 없이)

		{format}
		""";

	private static final String MENU_RERANK_USER_PROMPT_TEMPLATE = """
		당신은 커피 탐험 앱 'Comeet'의 바리스타 가이드입니다.
		사용자가 근처 카페에서 자신의 취향에 맞는 메뉴를 발견할 수 있도록 친근하게 안내해주세요.

		## 사용자 취향 프로필
		- 선호 산미: %d/10
		- 선호 바디감: %d/10
		- 선호 단맛: %d/10
		- 선호 쓴맛: %d/10
		- 선호 배전도: %s
		- 좋아하는 플레이버: %s

		## 추천 후보 메뉴
		%s

		## 추천 이유 작성 가이드
		- 반말(~해요, ~거든요, ~이에요)로 친근하게 작성
		- 메뉴의 특징과 원두의 매력을 연결해서 표현
		- 다양한 표현 사용 (맛 묘사, 분위기, 추천 상황 등)
		- 1~2문장으로 간결하게

		좋은 예시:
		- "진한 초콜릿 향이 감도는 드립이에요. 달달한 디저트랑 같이 즐겨보세요!"
		- "과일향 가득한 케냐 원두로 내린 커피예요. 상큼한 맛을 찾는다면 여기!"
		- "부드러운 산미가 매력적인 메뉴예요. 커피 입문자에게도 추천해요."
		- "묵직하고 고소한 맛이 일품이에요. 에스프레소 좋아하시면 꼭 드셔보세요!"

		피해야 할 표현:
		- "~에 잘 맞습니다", "균형 잡혀 있습니다" 같은 딱딱한 표현
		- 모든 추천에 비슷한 패턴 반복

		## 지시사항
		1. 사용자 취향에 가장 잘 맞는 메뉴 5개를 선정
		2. 메뉴 특징 + 원두 플레이버를 종합해서 추천 이유 작성
		3. 각 메뉴마다 개성 있는 스타일로 작성 (반복 금지)
		4. 반드시 아래 JSON 형식으로만 응답 (다른 텍스트 없이)

		{format}
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

		var outputConverter = new BeanOutputConverter<>(RerankResponse.class);

		String userPrompt = String.format(
			RERANK_USER_PROMPT_TEMPLATE,
			request.userPreference().prefAcidity(),
			request.userPreference().prefBody(),
			request.userPreference().prefSweetness(),
			request.userPreference().prefBitterness(),
			String.join(", ", request.userPreference().preferredRoastLevels()),
			String.join(", ", request.userPreference().likedTags()),
			candidatesJson
		).replace("{format}", outputConverter.getFormat());

		try {
			String response = openAiChatClient.prompt()
				.user(userPrompt)
				.call()
				.content();

			log.debug("LLM response: {}", response);

			return outputConverter.convert(response);
		} catch (Exception e) {
			log.error("Failed to rerank", e);
			throw new RuntimeException("Reranking failed", e);
		}
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

	/**
	 * 후보 메뉴들을 리랭킹하여 Top 3 추천
	 *
	 * @param request 사용자 취향 및 후보 메뉴 정보
	 * @return 리랭킹된 Top 3 메뉴 추천 결과
	 */
	public MenuRerankResponse rerankMenus(MenuRerankRequest request) {
		log.debug("Reranking {} menu candidates for user preference", request.menuCandidates().size());

		String candidatesJson = formatMenuCandidates(request);

		var outputConverter = new BeanOutputConverter<>(MenuRerankResponse.class);

		String userPrompt = String.format(
			MENU_RERANK_USER_PROMPT_TEMPLATE,
			request.userPreference().prefAcidity(),
			request.userPreference().prefBody(),
			request.userPreference().prefSweetness(),
			request.userPreference().prefBitterness(),
			String.join(", ", request.userPreference().preferredRoastLevels()),
			String.join(", ", request.userPreference().likedTags()),
			candidatesJson
		).replace("{format}", outputConverter.getFormat());

		try {
			String response = openAiChatClient.prompt()
				.user(userPrompt)
				.call()
				.content();

			log.debug("LLM menu rerank response: {}", response);

			return outputConverter.convert(response);
		} catch (Exception e) {
			log.error("Failed to rerank menus", e);
			throw new RuntimeException("Menu reranking failed", e);
		}
	}

	private String formatMenuCandidates(MenuRerankRequest request) {
		StringBuilder sb = new StringBuilder();
		int index = 1;
		for (RerankRequest.MenuCandidateInfo candidate : request.menuCandidates()) {
			sb.append(String.format(
				"%d. [메뉴ID:%d] %s\n" +
					"   - 메뉴 설명: %s\n" +
					"   - 사용 원두: %s (%s, %s)\n" +
					"   - 원두 플레이버: %s\n" +
					"   - 산미:%d, 바디:%d, 단맛:%d, 쓴맛:%d\n" +
					"   - 유사도: %.2f\n\n",
				index++,
				candidate.menuId(),
				candidate.menuName(),
				candidate.menuDescription() != null ? candidate.menuDescription() : "설명 없음",
				candidate.beanName(),
				candidate.roasteryName() != null ? candidate.roasteryName() : "로스터리 미상",
				candidate.roastLevel(),
				String.join(", ", candidate.flavorTags()),
				candidate.acidity(),
				candidate.body(),
				candidate.sweetness(),
				candidate.bitterness(),
				candidate.similarityScore()
			));
		}
		return sb.toString();
	}
}
