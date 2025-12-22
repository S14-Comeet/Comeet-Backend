package com.backend.domain.recommendation.mapper.query;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.backend.domain.recommendation.dto.internal.MenuWithBeanScoreDto;

/**
 * 추천 쿼리 Mapper
 */
@Mapper
public interface RecommendationQueryMapper {

	/**
	 * 필터링된 메뉴 조회 (하드 필터링 + 위치 필터링)
	 *
	 * @param dislikedTags         비선호 태그 목록 (bean_flavor_notes를 통해 필터링)
	 * @param preferredRoastLevels 선호 로스트 레벨 목록
	 * @param minLatitude          최소 위도 (LOCAL 모드 시)
	 * @param maxLatitude          최대 위도 (LOCAL 모드 시)
	 * @param minLongitude         최소 경도 (LOCAL 모드 시)
	 * @param maxLongitude         최대 경도 (LOCAL 모드 시)
	 * @return 필터링된 메뉴 목록
	 */
	List<MenuWithBeanScoreDto> findFilteredMenus(
		@Param("dislikedTags") List<String> dislikedTags,
		@Param("preferredRoastLevels") List<String> preferredRoastLevels,
		@Param("minLatitude") BigDecimal minLatitude,
		@Param("maxLatitude") BigDecimal maxLatitude,
		@Param("minLongitude") BigDecimal minLongitude,
		@Param("maxLongitude") BigDecimal maxLongitude
	);

	/**
	 * 특정 원두를 사용하는 메뉴 조회
	 *
	 * @param beanId       원두 ID
	 * @param minLatitude  최소 위도 (LOCAL 모드 시)
	 * @param maxLatitude  최대 위도 (LOCAL 모드 시)
	 * @param minLongitude 최소 경도 (LOCAL 모드 시)
	 * @param maxLongitude 최대 경도 (LOCAL 모드 시)
	 * @return 해당 원두를 사용하는 메뉴 목록
	 */
	List<MenuWithBeanScoreDto> findMenusByBeanId(
		@Param("beanId") Long beanId,
		@Param("minLatitude") BigDecimal minLatitude,
		@Param("maxLatitude") BigDecimal maxLatitude,
		@Param("minLongitude") BigDecimal minLongitude,
		@Param("maxLongitude") BigDecimal maxLongitude
	);
}
