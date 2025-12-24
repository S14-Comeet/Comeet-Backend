package com.backend.domain.recommendation.mapper.query;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.backend.domain.recommendation.dto.internal.MenuWithBeanScoreDto;

@Mapper
public interface RecommendationQueryMapper {

	List<MenuWithBeanScoreDto> findFilteredMenus(
		@Param("dislikedTags") List<String> dislikedTags,
		@Param("preferredRoastLevels") List<String> preferredRoastLevels,
		@Param("minLatitude") BigDecimal minLatitude,
		@Param("maxLatitude") BigDecimal maxLatitude,
		@Param("minLongitude") BigDecimal minLongitude,
		@Param("maxLongitude") BigDecimal maxLongitude
	);

	List<MenuWithBeanScoreDto> findMenusByBeanId(
		@Param("beanId") Long beanId,
		@Param("minLatitude") BigDecimal minLatitude,
		@Param("maxLatitude") BigDecimal maxLatitude,
		@Param("minLongitude") BigDecimal minLongitude,
		@Param("maxLongitude") BigDecimal maxLongitude
	);
}
