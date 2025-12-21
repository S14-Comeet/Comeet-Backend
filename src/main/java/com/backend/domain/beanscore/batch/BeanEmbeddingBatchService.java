package com.backend.domain.beanscore.batch;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.common.ai.service.EmbeddingService;
import com.backend.common.redis.service.RedisVectorService;
import com.backend.domain.beanscore.entity.BeanScore;
import com.backend.domain.beanscore.service.query.BeanScoreQueryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 원두 임베딩 배치 서비스
 *
 * 모든 bean_scores의 flavor_tags를 임베딩하여 Redis Vector에 저장
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BeanEmbeddingBatchService {

	private final BeanScoreQueryService beanScoreQueryService;
	private final EmbeddingService embeddingService;
	private final RedisVectorService redisVectorService;

	private static final int BATCH_SIZE = 50;

	/**
	 * 모든 원두 임베딩 생성 및 저장
	 *
	 * @return 처리된 원두 수
	 */
	@Transactional(readOnly = true)
	public int embedAllBeans() {
		log.info("Starting bean embedding batch process");

		List<BeanScore> allBeanScores = beanScoreQueryService.findAll();
		log.info("Found {} bean scores to process", allBeanScores.size());

		AtomicInteger successCount = new AtomicInteger(0);
		AtomicInteger failCount = new AtomicInteger(0);

		// 배치 처리
		for (int i = 0; i < allBeanScores.size(); i += BATCH_SIZE) {
			int end = Math.min(i + BATCH_SIZE, allBeanScores.size());
			List<BeanScore> batch = allBeanScores.subList(i, end);

			processBatch(batch, successCount, failCount);

			log.info("Processed batch {}/{}", end, allBeanScores.size());
		}

		log.info("Bean embedding batch completed. Success: {}, Failed: {}",
			successCount.get(), failCount.get());

		return successCount.get();
	}

	/**
	 * 배치 처리
	 */
	private void processBatch(List<BeanScore> batch, AtomicInteger successCount, AtomicInteger failCount) {
		for (BeanScore beanScore : batch) {
			try {
				if (beanScore.getFlavorTags() == null || beanScore.getFlavorTags().isEmpty()) {
					log.debug("Skipping bean {} - no flavor tags", beanScore.getBeanId());
					continue;
				}

				float[] embedding = embeddingService.embedTags(beanScore.getFlavorTags());
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
	 * 특정 원두 임베딩 업데이트
	 *
	 * @param beanId     원두 ID
	 * @param flavorTags 플레이버 태그
	 */
	public void updateEmbedding(Long beanId, List<String> flavorTags) {
		if (flavorTags == null || flavorTags.isEmpty()) {
			log.debug("Skipping embedding update for bean {} - no flavor tags", beanId);
			return;
		}

		try {
			float[] embedding = embeddingService.embedTags(flavorTags);
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
	 * 임베딩이 없는 원두만 처리
	 *
	 * @return 처리된 원두 수
	 */
	@Transactional(readOnly = true)
	public int embedMissingBeans() {
		log.info("Starting missing bean embedding process");

		List<BeanScore> allBeanScores = beanScoreQueryService.findAll();
		AtomicInteger processedCount = new AtomicInteger(0);

		for (BeanScore beanScore : allBeanScores) {
			if (beanScore.getFlavorTags() == null || beanScore.getFlavorTags().isEmpty()) {
				continue;
			}

			if (!redisVectorService.existsEmbedding(beanScore.getBeanId())) {
				try {
					float[] embedding = embeddingService.embedTags(beanScore.getFlavorTags());
					redisVectorService.saveEmbedding(beanScore.getBeanId(), embedding);
					processedCount.incrementAndGet();
					log.debug("Created missing embedding for bean {}", beanScore.getBeanId());
				} catch (Exception e) {
					log.error("Failed to create embedding for bean {}", beanScore.getBeanId(), e);
				}
			}
		}

		log.info("Missing bean embedding completed. Processed: {}", processedCount.get());
		return processedCount.get();
	}
}
