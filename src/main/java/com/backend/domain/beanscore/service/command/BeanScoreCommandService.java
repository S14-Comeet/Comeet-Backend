package com.backend.domain.beanscore.service.command;

import com.backend.domain.beanscore.entity.BeanScore;

/**
 * BeanScore Command Service Interface
 */
public interface BeanScoreCommandService {

	/**
	 * 원두 점수 저장
	 */
	void save(BeanScore beanScore);

	/**
	 * 원두 점수 업데이트
	 */
	void update(BeanScore beanScore);

	/**
	 * 원두 점수 삭제
	 */
	void deleteByBeanId(Long beanId);
}
