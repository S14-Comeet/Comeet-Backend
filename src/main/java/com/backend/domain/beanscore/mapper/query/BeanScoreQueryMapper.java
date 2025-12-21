package com.backend.domain.beanscore.mapper.query;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.backend.domain.beanscore.dto.response.BeanScoreWithBeanDto;
import com.backend.domain.beanscore.entity.BeanScore;

/**
 * BeanScore Query Mapper
 */
@Mapper
public interface BeanScoreQueryMapper {

	/**
	 * beanId로 점수 조회
	 */
	Optional<BeanScore> findByBeanId(@Param("beanId") Long beanId);

	/**
	 * beanId로 점수 존재 여부 확인
	 */
	boolean existsByBeanId(@Param("beanId") Long beanId);

	/**
	 * 모든 원두 점수 조회 (임베딩용)
	 */
	List<BeanScore> findAll();

	/**
	 * Bean 정보와 함께 조회
	 */
	Optional<BeanScoreWithBeanDto> findWithBeanByBeanId(@Param("beanId") Long beanId);

	/**
	 * 여러 beanId로 조회 (Bean 정보 포함)
	 */
	List<BeanScoreWithBeanDto> findWithBeanByBeanIds(@Param("beanIds") List<Long> beanIds);

	/**
	 * 하드 필터링된 원두 점수 조회 (추천용)
	 */
	List<BeanScoreWithBeanDto> findFilteredBeanScores(
		@Param("dislikedTagsJson") String dislikedTagsJson,
		@Param("preferredRoastLevels") List<String> preferredRoastLevels
	);
}
