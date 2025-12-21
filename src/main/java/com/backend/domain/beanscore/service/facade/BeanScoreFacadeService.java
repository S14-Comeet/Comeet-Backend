package com.backend.domain.beanscore.service.facade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.common.ai.service.EmbeddingService;
import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.BeanScoreException;
import com.backend.common.redis.service.RedisVectorService;
import com.backend.domain.beanscore.converter.BeanScoreConverter;
import com.backend.domain.beanscore.dto.request.BeanScoreUpdateReqDto;
import com.backend.domain.beanscore.dto.response.BeanScoreResDto;
import com.backend.domain.beanscore.entity.BeanScore;
import com.backend.domain.beanscore.service.command.BeanScoreCommandService;
import com.backend.domain.beanscore.service.query.BeanScoreQueryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * BeanScore Facade Service
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BeanScoreFacadeService {

	private final BeanScoreCommandService beanScoreCommandService;
	private final BeanScoreQueryService beanScoreQueryService;
	private final EmbeddingService embeddingService;
	private final RedisVectorService redisVectorService;

	/**
	 * 원두 점수 조회
	 */
	@Transactional(readOnly = true)
	public BeanScoreResDto getBeanScore(Long beanId) {
		BeanScore beanScore = beanScoreQueryService.getByBeanId(beanId);
		return BeanScoreConverter.toResDto(beanScore);
	}

	/**
	 * 원두 점수 생성
	 */
	@Transactional
	public BeanScoreResDto createBeanScore(Long beanId, BeanScoreUpdateReqDto reqDto) {
		if (beanScoreQueryService.existsByBeanId(beanId)) {
			throw new BeanScoreException(ErrorCode.BEAN_SCORE_ALREADY_EXISTS);
		}

		BeanScore beanScore = BeanScore.createDefault(beanId);
		beanScore.update(
			reqDto.acidity(),
			reqDto.body(),
			reqDto.sweetness(),
			reqDto.bitterness(),
			reqDto.aroma(),
			reqDto.flavor(),
			reqDto.aftertaste(),
			reqDto.totalScore(),
			reqDto.roastLevel(),
			reqDto.flavorTags()
		);

		beanScoreCommandService.save(beanScore);

		// 임베딩 생성 및 저장
		if (reqDto.flavorTags() != null && !reqDto.flavorTags().isEmpty()) {
			updateEmbedding(beanId, reqDto.flavorTags());
		}

		return BeanScoreConverter.toResDto(beanScore);
	}

	/**
	 * 원두 점수 업데이트
	 */
	@Transactional
	public BeanScoreResDto updateBeanScore(Long beanId, BeanScoreUpdateReqDto reqDto) {
		BeanScore beanScore = beanScoreQueryService.findByBeanId(beanId)
			.orElseGet(() -> {
				BeanScore newBeanScore = BeanScore.createDefault(beanId);
				beanScoreCommandService.save(newBeanScore);
				return newBeanScore;
			});

		beanScore.update(
			reqDto.acidity(),
			reqDto.body(),
			reqDto.sweetness(),
			reqDto.bitterness(),
			reqDto.aroma(),
			reqDto.flavor(),
			reqDto.aftertaste(),
			reqDto.totalScore(),
			reqDto.roastLevel(),
			reqDto.flavorTags()
		);

		beanScoreCommandService.update(beanScore);

		// 임베딩 업데이트
		if (reqDto.flavorTags() != null && !reqDto.flavorTags().isEmpty()) {
			updateEmbedding(beanId, reqDto.flavorTags());
		}

		return BeanScoreConverter.toResDto(beanScore);
	}

	/**
	 * 원두 점수 삭제
	 */
	@Transactional
	public void deleteBeanScore(Long beanId) {
		if (!beanScoreQueryService.existsByBeanId(beanId)) {
			throw new BeanScoreException(ErrorCode.BEAN_SCORE_NOT_FOUND);
		}
		beanScoreCommandService.deleteByBeanId(beanId);
		redisVectorService.deleteEmbedding(beanId);
	}

	/**
	 * 임베딩 업데이트
	 */
	private void updateEmbedding(Long beanId, java.util.List<String> flavorTags) {
		try {
			float[] embedding = embeddingService.embedTags(flavorTags);
			redisVectorService.saveEmbedding(beanId, embedding);
			log.debug("Updated embedding for bean {}", beanId);
		} catch (Exception e) {
			log.error("Failed to update embedding for bean {}", beanId, e);
		}
	}
}
