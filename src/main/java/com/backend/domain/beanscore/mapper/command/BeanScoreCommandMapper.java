package com.backend.domain.beanscore.mapper.command;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.backend.domain.beanscore.entity.BeanScore;

/**
 * BeanScore Command Mapper
 */
@Mapper
public interface BeanScoreCommandMapper {

	/**
	 * 원두 점수 저장
	 */
	int insert(@Param("beanScore") BeanScore beanScore);

	/**
	 * 원두 점수 업데이트
	 */
	int update(@Param("beanScore") BeanScore beanScore);

	/**
	 * 원두 점수 삭제
	 */
	int deleteByBeanId(@Param("beanId") Long beanId);
}
