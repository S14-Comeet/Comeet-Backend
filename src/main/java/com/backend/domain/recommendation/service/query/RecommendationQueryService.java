package com.backend.domain.recommendation.service.query;

import java.math.BigDecimal;
import java.util.List;

import com.backend.common.util.GeoUtils;
import com.backend.domain.recommendation.dto.internal.MenuWithBeanScoreDto;

/**
 * 추천 쿼리 서비스 인터페이스
 */
public interface RecommendationQueryService {

	/**
	 * 하드 필터링된 메뉴 조회
	 *
	 * @param dislikedTagsJson     비선호 태그 JSON
	 * @param preferredRoastLevels 선호 로스트 레벨 목록
	 * @param boundingBox          위치 필터링용 BoundingBox (null이면 전역 검색)
	 * @return 필터링된 메뉴 목록
	 */
	List<MenuWithBeanScoreDto> findFilteredMenus(
		String dislikedTagsJson,
		List<String> preferredRoastLevels,
		GeoUtils.BoundingBox boundingBox
	);

	/**
	 * 특정 원두를 사용하는 메뉴 조회
	 *
	 * @param beanId      원두 ID
	 * @param boundingBox 위치 필터링용 BoundingBox (null이면 전역 검색)
	 * @return 해당 원두를 사용하는 메뉴 목록
	 */
	List<MenuWithBeanScoreDto> findMenusByBeanId(Long beanId, GeoUtils.BoundingBox boundingBox);

	/**
	 * 메뉴와 사용자 위치 간 거리 계산
	 *
	 * @param menu      메뉴 정보
	 * @param latitude  사용자 위도
	 * @param longitude 사용자 경도
	 * @return 거리 (km)
	 */
	Double calculateDistance(MenuWithBeanScoreDto menu, BigDecimal latitude, BigDecimal longitude);
}
