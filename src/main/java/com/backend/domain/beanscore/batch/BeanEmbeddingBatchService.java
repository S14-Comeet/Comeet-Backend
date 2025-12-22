package com.backend.domain.beanscore.batch;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.common.ai.service.EmbeddingService;
import com.backend.common.ai.util.EmbeddingTextBuilder;
import com.backend.common.redis.service.RedisVectorService;
import com.backend.domain.bean.service.query.BeanFlavorQueryService;
import com.backend.domain.beanscore.entity.BeanScore;
import com.backend.domain.beanscore.service.query.BeanScoreQueryService;
import com.backend.domain.flavor.entity.Flavor;
import com.backend.domain.flavor.service.FlavorQueryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 원두 임베딩 배치 서비스
 *
 * bean_flavor_notes의 flavor를 임베딩하여 Redis Vector에 저장
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BeanEmbeddingBatchService {

	private final BeanScoreQueryService beanScoreQueryService;
	private final BeanFlavorQueryService beanFlavorQueryService;
	private final FlavorQueryService flavorQueryService;
	private final EmbeddingService embeddingService;
	private final RedisVectorService redisVectorService;

	private static final int BATCH_SIZE = 50;

	/**
	 * 모든 원두 임베딩 생성 및 저장 (flavor가 있는 원두만)
	 *
	 * @return 처리된 원두 수
	 */
	@Transactional(readOnly = true)
	public int embedAllBeans() {
		log.info("Starting bean embedding batch process (only beans with flavors)");

		List<BeanScore> allBeanScores = beanScoreQueryService.findAll();
		log.info("Found {} bean scores to process", allBeanScores.size());

		AtomicInteger successCount = new AtomicInteger(0);
		AtomicInteger skipCount = new AtomicInteger(0);
		AtomicInteger failCount = new AtomicInteger(0);

		// 배치 처리
		for (int i = 0; i < allBeanScores.size(); i += BATCH_SIZE) {
			int end = Math.min(i + BATCH_SIZE, allBeanScores.size());
			List<BeanScore> batch = allBeanScores.subList(i, end);

			processBatch(batch, successCount, skipCount, failCount);

			log.info("Processed batch {}/{}", end, allBeanScores.size());
		}

		log.info("Bean embedding batch completed. Success: {}, Skipped (no flavor): {}, Failed: {}",
			successCount.get(), skipCount.get(), failCount.get());

		return successCount.get();
	}

	/**
	 * 기존 임베딩 전체 삭제 후 재생성 (flavor가 있는 원두만)
	 *
	 * @return 결과 정보
	 */
	@Transactional(readOnly = true)
	public EmbedResult dropAndEmbedAll() {
		log.info("Dropping all embeddings and re-embedding beans with flavors");

		// 1. 기존 임베딩 전체 삭제
		int deletedCount = redisVectorService.dropAllEmbeddings();
		log.info("Dropped {} existing embeddings", deletedCount);

		// 2. flavor가 있는 원두만 임베딩
		int embeddedCount = embedAllBeans();

		return new EmbedResult(deletedCount, embeddedCount);
	}

	public record EmbedResult(int deletedCount, int embeddedCount) {}

	/**
	 * 배치 처리 (flavor가 있는 원두만 임베딩)
	 */
	private void processBatch(List<BeanScore> batch, AtomicInteger successCount, AtomicInteger skipCount,
		AtomicInteger failCount) {
		for (BeanScore beanScore : batch) {
			try {
				// bean_flavor_notes에서 flavor 조회
				List<Flavor> flavors = beanFlavorQueryService.findFlavorsByBeanId(beanScore.getBeanId());

				// flavor가 없으면 임베딩 skip
				if (flavors == null || flavors.isEmpty()) {
					log.debug("Skipping bean {} - no flavors mapped", beanScore.getBeanId());
					skipCount.incrementAndGet();
					continue;
				}

				// 플레이버 태그를 계층 구조 경로로 변환
				List<String> flavorCodes = flavors.stream()
					.map(Flavor::getCode)
					.toList();
				List<String> hierarchyPaths = flavorQueryService.getHierarchyPaths(flavorCodes);

				// 점수 + 플레이버를 조합한 임베딩 텍스트 생성
				String embeddingText = EmbeddingTextBuilder.buildBeanEmbeddingText(
					beanScore.getAcidity(),
					beanScore.getBody(),
					beanScore.getSweetness(),
					beanScore.getBitterness(),
					beanScore.getRoastLevel(),
					hierarchyPaths
				);
				log.debug("Bean {} embedding text: {}", beanScore.getBeanId(), embeddingText);

				float[] embedding = embeddingService.embed(embeddingText);
				redisVectorService.saveEmbedding(beanScore.getBeanId(), embedding);
				successCount.incrementAndGet();

				log.debug("Embedded bean {}", beanScore.getBeanId());
			} catch (Exception e) {
				log.error("Failed to embed bean {}", beanScore.getBeanId(), e);
				failCount.incrementAndGet();
			}
		}
	}

	/**
	 * 특정 원두 임베딩 업데이트 (flavor가 없으면 삭제)
	 *
	 * @param beanId 원두 ID
	 */
	public void updateEmbedding(Long beanId) {
		try {
			// BeanScore 조회
			BeanScore beanScore = beanScoreQueryService.findByBeanId(beanId).orElse(null);
			if (beanScore == null) {
				log.debug("Skipping embedding update for bean {} - no bean score", beanId);
				redisVectorService.deleteEmbedding(beanId);
				return;
			}

			// bean_flavor_notes에서 flavor 조회
			List<Flavor> flavors = beanFlavorQueryService.findFlavorsByBeanId(beanId);

			// flavor가 없으면 기존 임베딩 삭제
			if (flavors == null || flavors.isEmpty()) {
				log.debug("Deleting embedding for bean {} - no flavors mapped", beanId);
				redisVectorService.deleteEmbedding(beanId);
				return;
			}

			// 플레이버 태그를 계층 구조 경로로 변환
			List<String> flavorCodes = flavors.stream()
				.map(Flavor::getCode)
				.toList();
			List<String> hierarchyPaths = flavorQueryService.getHierarchyPaths(flavorCodes);

			// 점수 + 플레이버를 조합한 임베딩 텍스트 생성
			String embeddingText = EmbeddingTextBuilder.buildBeanEmbeddingText(
				beanScore.getAcidity(),
				beanScore.getBody(),
				beanScore.getSweetness(),
				beanScore.getBitterness(),
				beanScore.getRoastLevel(),
				hierarchyPaths
			);
			log.debug("Bean {} embedding text: {}", beanId, embeddingText);

			float[] embedding = embeddingService.embed(embeddingText);
			redisVectorService.saveEmbedding(beanId, embedding);
			log.debug("Updated embedding for bean {}", beanId);
		} catch (Exception e) {
			log.error("Failed to update embedding for bean {}", beanId, e);
		}
	}

	/**
	 * 특정 원두 임베딩 삭제
	 *
	 * @param beanId 원두 ID
	 */
	public void deleteEmbedding(Long beanId) {
		try {
			redisVectorService.deleteEmbedding(beanId);
			log.debug("Deleted embedding for bean {}", beanId);
		} catch (Exception e) {
			log.error("Failed to delete embedding for bean {}", beanId, e);
		}
	}

	/**
	 * 임베딩이 없는 원두만 처리 (flavor가 있는 원두만)
	 *
	 * @return 처리된 원두 수
	 */
	@Transactional(readOnly = true)
	public int embedMissingBeans() {
		log.info("Starting missing bean embedding process (only beans with flavors)");

		List<BeanScore> allBeanScores = beanScoreQueryService.findAll();
		AtomicInteger processedCount = new AtomicInteger(0);
		AtomicInteger skipCount = new AtomicInteger(0);

		for (BeanScore beanScore : allBeanScores) {
			if (!redisVectorService.existsEmbedding(beanScore.getBeanId())) {
				try {
					// bean_flavor_notes에서 flavor 조회
					List<Flavor> flavors = beanFlavorQueryService.findFlavorsByBeanId(beanScore.getBeanId());

					// flavor가 없으면 skip
					if (flavors == null || flavors.isEmpty()) {
						log.debug("Skipping bean {} - no flavors mapped", beanScore.getBeanId());
						skipCount.incrementAndGet();
						continue;
					}

					// 플레이버 태그를 계층 구조 경로로 변환
					List<String> flavorCodes = flavors.stream()
						.map(Flavor::getCode)
						.toList();
					List<String> hierarchyPaths = flavorQueryService.getHierarchyPaths(flavorCodes);

					// 점수 + 플레이버를 조합한 임베딩 텍스트 생성
					String embeddingText = EmbeddingTextBuilder.buildBeanEmbeddingText(
						beanScore.getAcidity(),
						beanScore.getBody(),
						beanScore.getSweetness(),
						beanScore.getBitterness(),
						beanScore.getRoastLevel(),
						hierarchyPaths
					);
					log.debug("Bean {} embedding text: {}", beanScore.getBeanId(), embeddingText);

					float[] embedding = embeddingService.embed(embeddingText);
					redisVectorService.saveEmbedding(beanScore.getBeanId(), embedding);
					processedCount.incrementAndGet();
					log.debug("Created missing embedding for bean {}", beanScore.getBeanId());
				} catch (Exception e) {
					log.error("Failed to create embedding for bean {}", beanScore.getBeanId(), e);
				}
			}
		}

		log.info("Missing bean embedding completed. Processed: {}, Skipped (no flavor): {}",
			processedCount.get(), skipCount.get());
		return processedCount.get();
	}
}
