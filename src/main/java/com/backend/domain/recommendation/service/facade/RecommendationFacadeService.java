package com.backend.domain.recommendation.service.facade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.common.ai.dto.RerankRequest;
import com.backend.common.ai.dto.RerankResponse;
import com.backend.common.ai.service.EmbeddingService;
import com.backend.common.ai.service.LlmService;
import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.RecommendationException;
import com.backend.common.redis.dto.VectorSearchResult;
import com.backend.common.redis.service.RedisVectorService;
import com.backend.common.util.GeoUtils;
import com.backend.domain.bean.dto.common.BeanBadgeDto;
import com.backend.domain.beanscore.dto.response.BeanScoreWithBeanDto;
import com.backend.domain.beanscore.service.query.BeanScoreQueryService;
import com.backend.domain.preference.entity.UserPreference;
import com.backend.domain.preference.service.query.PreferenceQueryService;
import com.backend.domain.recommendation.dto.internal.MenuWithBeanScoreDto;
import com.backend.domain.recommendation.dto.request.RecommendationReqDto;
import com.backend.domain.recommendation.dto.response.BeanRecommendationResDto;
import com.backend.domain.recommendation.dto.response.MenuRecommendationResDto;
import com.backend.domain.recommendation.service.query.RecommendationQueryService;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 추천 시스템 Facade 서비스
 *
 * 추천 프로세스:
 * 1. 위치 필터링 (LOCAL 모드)
 * 2. 하드 필터링 (disliked_tags, roast_level)
 * 3. 벡터 검색 (liked_tags 유사도)
 * 4. LLM 리랭킹 (Top 3 선정)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationFacadeService {

	private final PreferenceQueryService preferenceQueryService;
	private final BeanScoreQueryService beanScoreQueryService;
	private final RecommendationQueryService recommendationQueryService;
	private final EmbeddingService embeddingService;
	private final RedisVectorService redisVectorService;
	private final LlmService llmService;
	private final Gson gson = new Gson();

	private static final int VECTOR_SEARCH_TOP_K = 20;
	private static final int FINAL_RECOMMENDATION_COUNT = 3;

	/**
	 * 원두 추천 (전역)
	 */
	@Transactional(readOnly = true)
	public List<BeanRecommendationResDto> recommendBeans(Long userId) {
		log.info("Starting bean recommendation for user {}", userId);

		// 1. 사용자 취향 조회
		UserPreference preference = preferenceQueryService.findByUserId(userId)
			.orElseGet(() -> UserPreference.createDefault(userId));

		// 2. 하드 필터링 (SQL)
		String dislikedTagsJson = convertToJson(preference.getDislikedTags());
		List<String> preferredRoastLevels = convertRoastLevelsToStrings(preference);

		List<BeanScoreWithBeanDto> filteredBeans = beanScoreQueryService.findFilteredBeanScores(
			preference.getDislikedTags(), preference.getPreferredRoastLevels().stream()
				.map(Enum::name)
				.toList()
		);

		if (filteredBeans.isEmpty()) {
			log.warn("No beans found after hard filtering for user {}", userId);
			return List.of();
		}

		log.debug("Found {} beans after hard filtering", filteredBeans.size());

		// 3. 벡터 검색 (liked_tags 기반)
		List<Long> beanIds = filteredBeans.stream()
			.map(BeanScoreWithBeanDto::beanId)
			.toList();

		List<VectorSearchResult> vectorResults = performVectorSearch(preference.getLikedTags(), beanIds);

		if (vectorResults.isEmpty()) {
			log.warn("No vector search results for user {}", userId);
			// 벡터 검색 결과가 없으면 필터링된 원두 중 상위 3개 반환
			return filteredBeans.stream()
				.limit(FINAL_RECOMMENDATION_COUNT)
				.map(bean -> createBeanRecommendation(bean, filteredBeans.indexOf(bean) + 1, 0.0, "취향에 맞는 원두입니다."))
				.toList();
		}

		// 4. LLM 리랭킹
		Map<Long, BeanScoreWithBeanDto> beanMap = filteredBeans.stream()
			.collect(Collectors.toMap(BeanScoreWithBeanDto::beanId, b -> b));

		Map<Long, Double> similarityMap = vectorResults.stream()
			.collect(Collectors.toMap(VectorSearchResult::beanId, VectorSearchResult::score));

		List<RerankRequest.CandidateInfo> candidates = vectorResults.stream()
			.filter(vr -> beanMap.containsKey(vr.beanId()))
			.map(vr -> {
				BeanScoreWithBeanDto bean = beanMap.get(vr.beanId());
				return new RerankRequest.CandidateInfo(
					bean.beanId(),
					bean.beanName(),
					bean.roasteryName(),
					bean.country(),
					bean.roastLevel() != null ? bean.roastLevel().name() : "MEDIUM",
					bean.flavorTags() != null ? bean.flavorTags() : List.of(),
					bean.acidity(),
					bean.body(),
					bean.sweetness(),
					bean.bitterness(),
					vr.score()
				);
			})
			.toList();

		RerankRequest rerankRequest = new RerankRequest(
			new RerankRequest.UserPreferenceInfo(
				preference.getPrefAcidity(),
				preference.getPrefBody(),
				preference.getPrefSweetness(),
				preference.getPrefBitterness(),
				preferredRoastLevels,
				preference.getLikedTags() != null ? preference.getLikedTags() : List.of()
			),
			candidates
		);

		try {
			RerankResponse rerankResponse = llmService.rerank(rerankRequest);

			return rerankResponse.recommendations().stream()
				.map(rec -> {
					BeanScoreWithBeanDto bean = beanMap.get(rec.beanId());
					if (bean == null) {
						return null;
					}
					Double similarity = similarityMap.getOrDefault(rec.beanId(), 0.0);
					return createBeanRecommendation(bean, rec.rank(), similarity, rec.reason());
				})
				.filter(r -> r != null)
				.toList();

		} catch (Exception e) {
			log.error("LLM reranking failed, falling back to vector search results", e);
			// LLM 실패시 벡터 검색 결과로 대체
			return vectorResults.stream()
				.limit(FINAL_RECOMMENDATION_COUNT)
				.map(vr -> {
					BeanScoreWithBeanDto bean = beanMap.get(vr.beanId());
					if (bean == null) return null;
					return createBeanRecommendation(bean, vectorResults.indexOf(vr) + 1,
						vr.score(), "취향과 유사한 플레이버를 가진 원두입니다.");
				})
				.filter(r -> r != null)
				.toList();
		}
	}

	/**
	 * 메뉴 추천
	 */
	@Transactional(readOnly = true)
	public List<MenuRecommendationResDto> recommendMenus(Long userId, RecommendationReqDto reqDto) {
		log.info("Starting menu recommendation for user {} with type {}", userId, reqDto.type());

		// 1. 사용자 취향 조회
		UserPreference preference = preferenceQueryService.findByUserId(userId)
			.orElseGet(() -> UserPreference.createDefault(userId));

		// 2. 위치 필터링용 BoundingBox 계산 (LOCAL 모드)
		GeoUtils.BoundingBox boundingBox = null;
		if (reqDto.isLocal() && reqDto.hasValidLocation()) {
			boundingBox = GeoUtils.calculateBoundingBox(
				reqDto.latitude(),
				reqDto.longitude(),
				reqDto.radiusKm()
			);
			log.debug("LOCAL mode: BoundingBox calculated for radius {}km", reqDto.radiusKm());
		}

		// 3. 하드 필터링 (SQL)
		String dislikedTagsJson = convertToJson(preference.getDislikedTags());
		List<String> preferredRoastLevels = convertRoastLevelsToStrings(preference);

		List<MenuWithBeanScoreDto> filteredMenus = recommendationQueryService.findFilteredMenus(
			dislikedTagsJson,
			preferredRoastLevels,
			boundingBox
		);

		if (filteredMenus.isEmpty()) {
			log.warn("No menus found after filtering for user {}", userId);
			return List.of();
		}

		log.debug("Found {} menus after filtering", filteredMenus.size());

		// 4. 벡터 검색 (liked_tags 기반, 중복 beanId 제거)
		List<Long> beanIds = filteredMenus.stream()
			.map(MenuWithBeanScoreDto::beanId)
			.distinct()
			.toList();

		List<VectorSearchResult> vectorResults = performVectorSearch(preference.getLikedTags(), beanIds);

		// 5. 벡터 결과와 메뉴 매핑
		Map<Long, Double> beanSimilarityMap = vectorResults.stream()
			.collect(Collectors.toMap(VectorSearchResult::beanId, VectorSearchResult::score));

		// 메뉴에 유사도 점수 부여 및 정렬
		List<MenuWithScore> menuWithScores = filteredMenus.stream()
			.map(menu -> new MenuWithScore(
				menu,
				beanSimilarityMap.getOrDefault(menu.beanId(), 0.0),
				reqDto.isLocal() && reqDto.hasValidLocation()
					? recommendationQueryService.calculateDistance(menu, reqDto.latitude(), reqDto.longitude())
					: null
			))
			.sorted((a, b) -> Double.compare(b.similarity, a.similarity))
			.limit(VECTOR_SEARCH_TOP_K)
			.toList();

		if (menuWithScores.isEmpty()) {
			return List.of();
		}

		// 6. LLM 리랭킹
		List<RerankRequest.CandidateInfo> candidates = menuWithScores.stream()
			.map(ms -> new RerankRequest.CandidateInfo(
				ms.menu.beanId(),
				ms.menu.beanName(),
				ms.menu.roasteryName(),
				ms.menu.beanCountry(),
				ms.menu.roastLevel() != null ? ms.menu.roastLevel().name() : "MEDIUM",
				ms.menu.flavorTags() != null ? ms.menu.flavorTags() : List.of(),
				ms.menu.acidity(),
				ms.menu.body(),
				ms.menu.sweetness(),
				ms.menu.bitterness(),
				ms.similarity
			))
			.toList();

		RerankRequest rerankRequest = new RerankRequest(
			new RerankRequest.UserPreferenceInfo(
				preference.getPrefAcidity(),
				preference.getPrefBody(),
				preference.getPrefSweetness(),
				preference.getPrefBitterness(),
				preferredRoastLevels,
				preference.getLikedTags() != null ? preference.getLikedTags() : List.of()
			),
			candidates
		);

		try {
			RerankResponse rerankResponse = llmService.rerank(rerankRequest);

			// LLM 결과와 메뉴 매핑
			Map<Long, MenuWithScore> beanToMenuMap = new HashMap<>();
			for (MenuWithScore ms : menuWithScores) {
				// 같은 beanId를 가진 메뉴 중 첫 번째만 사용
				beanToMenuMap.putIfAbsent(ms.menu.beanId(), ms);
			}

			return rerankResponse.recommendations().stream()
				.map(rec -> {
					MenuWithScore ms = beanToMenuMap.get(rec.beanId());
					if (ms == null) return null;
					return createMenuRecommendation(ms.menu, rec.rank(), ms.similarity, rec.reason(), ms.distance);
				})
				.filter(r -> r != null)
				.toList();

		} catch (Exception e) {
			log.error("LLM reranking failed, falling back to vector search results", e);
			// LLM 실패시 벡터 검색 결과로 대체
			return menuWithScores.stream()
				.limit(FINAL_RECOMMENDATION_COUNT)
				.map(ms -> createMenuRecommendation(
					ms.menu,
					menuWithScores.indexOf(ms) + 1,
					ms.similarity,
					"취향과 유사한 플레이버의 메뉴입니다.",
					ms.distance
				))
				.toList();
		}
	}

	/**
	 * 특정 원두를 사용하는 메뉴 조회
	 */
	@Transactional(readOnly = true)
	public List<MenuRecommendationResDto> findMenusByBean(Long beanId, RecommendationReqDto reqDto) {
		log.info("Finding menus for bean {} with type {}", beanId, reqDto.type());

		GeoUtils.BoundingBox boundingBox = null;
		if (reqDto.isLocal() && reqDto.hasValidLocation()) {
			boundingBox = GeoUtils.calculateBoundingBox(
				reqDto.latitude(),
				reqDto.longitude(),
				reqDto.radiusKm()
			);
		}

		List<MenuWithBeanScoreDto> menus = recommendationQueryService.findMenusByBeanId(beanId, boundingBox);

		return menus.stream()
			.map(menu -> {
				Double distance = null;
				if (reqDto.isLocal() && reqDto.hasValidLocation()) {
					distance = recommendationQueryService.calculateDistance(
						menu, reqDto.latitude(), reqDto.longitude()
					);
				}
				return createMenuRecommendation(menu, null, null, null, distance);
			})
			.toList();
	}

	/**
	 * 벡터 검색 수행
	 */
	private List<VectorSearchResult> performVectorSearch(List<String> likedTags, List<Long> beanIds) {
		if (likedTags == null || likedTags.isEmpty()) {
			log.debug("No liked tags provided, skipping vector search");
			return List.of();
		}

		try {
			float[] queryEmbedding = embeddingService.embedTags(likedTags);
			return redisVectorService.searchSimilarInBeans(queryEmbedding, beanIds, VECTOR_SEARCH_TOP_K);
		} catch (Exception e) {
			log.error("Vector search failed", e);
			return List.of();
		}
	}

	private String convertToJson(List<String> tags) {
		if (tags == null || tags.isEmpty()) {
			return null;
		}
		return gson.toJson(tags);
	}

	private List<String> convertRoastLevelsToStrings(UserPreference preference) {
		if (preference.getPreferredRoastLevels() == null || preference.getPreferredRoastLevels().isEmpty()) {
			return List.of();
		}
		return preference.getPreferredRoastLevels().stream()
			.map(Enum::name)
			.toList();
	}

	private BeanRecommendationResDto createBeanRecommendation(
		BeanScoreWithBeanDto bean, int rank, Double similarity, String reason
	) {
		return BeanRecommendationResDto.builder()
			.beanId(bean.beanId())
			.beanName(bean.beanName())
			.description(null) // BeanScoreWithBeanDto에 description이 없음
			.origin(bean.country())
			.roastLevel(bean.roastLevel())
			.flavorTags(bean.flavorTags())
			.totalScore(bean.totalScore())
			.rank(rank)
			.reason(reason)
			.similarityScore(similarity)
			.build();
	}

	private MenuRecommendationResDto createMenuRecommendation(
		MenuWithBeanScoreDto menu, Integer rank, Double similarity, String reason, Double distance
	) {
		return MenuRecommendationResDto.builder()
			.menuId(menu.menuId())
			.menuName(menu.menuName())
			.menuDescription(menu.menuDescription())
			.price(menu.menuPrice())
			.menuImageUrl(menu.menuImageUrl())
			.storeId(menu.storeId())
			.storeName(menu.storeName())
			.storeAddress(menu.storeAddress())
			.storeLatitude(menu.storeLatitude())
			.storeLongitude(menu.storeLongitude())
			.distanceKm(distance)
			.beans(List.of(BeanBadgeDto.builder()
				.id(menu.beanId())
				.name(menu.beanName())
				.build()))
			.rank(rank)
			.reason(reason)
			.similarityScore(similarity)
			.build();
	}

	/**
	 * 메뉴와 유사도 점수를 함께 담는 내부 클래스
	 */
	private record MenuWithScore(
		MenuWithBeanScoreDto menu,
		Double similarity,
		Double distance
	) {
	}
}
