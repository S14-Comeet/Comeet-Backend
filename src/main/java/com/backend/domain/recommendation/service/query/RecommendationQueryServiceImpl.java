package com.backend.domain.recommendation.service.query;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.common.util.GeoUtils;
import com.backend.domain.recommendation.dto.internal.MenuWithBeanScoreDto;
import com.backend.domain.recommendation.mapper.query.RecommendationQueryMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 추천 쿼리 서비스 구현체
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecommendationQueryServiceImpl implements RecommendationQueryService {

	private final RecommendationQueryMapper recommendationQueryMapper;

	@Override
	public List<MenuWithBeanScoreDto> findFilteredMenus(
		String dislikedTagsJson,
		List<String> preferredRoastLevels,
		GeoUtils.BoundingBox boundingBox
	) {
		BigDecimal minLat = null, maxLat = null, minLon = null, maxLon = null;
		if (boundingBox != null) {
			minLat = boundingBox.minLatitude();
			maxLat = boundingBox.maxLatitude();
			minLon = boundingBox.minLongitude();
			maxLon = boundingBox.maxLongitude();
		}

		List<MenuWithBeanScoreDto> menus = recommendationQueryMapper.findFilteredMenus(
			dislikedTagsJson,
			preferredRoastLevels,
			minLat, maxLat, minLon, maxLon
		);

		log.debug("Found {} filtered menus", menus.size());
		return menus;
	}

	@Override
	public List<MenuWithBeanScoreDto> findMenusByBeanId(Long beanId, GeoUtils.BoundingBox boundingBox) {
		BigDecimal minLat = null, maxLat = null, minLon = null, maxLon = null;
		if (boundingBox != null) {
			minLat = boundingBox.minLatitude();
			maxLat = boundingBox.maxLatitude();
			minLon = boundingBox.minLongitude();
			maxLon = boundingBox.maxLongitude();
		}

		List<MenuWithBeanScoreDto> menus = recommendationQueryMapper.findMenusByBeanId(
			beanId, minLat, maxLat, minLon, maxLon
		);

		log.debug("Found {} menus using bean {}", menus.size(), beanId);
		return menus;
	}

	@Override
	public Double calculateDistance(MenuWithBeanScoreDto menu, BigDecimal latitude, BigDecimal longitude) {
		if (menu.storeLatitude() == null || menu.storeLongitude() == null
			|| latitude == null || longitude == null) {
			return null;
		}

		return GeoUtils.calculateHaversineDistance(
			latitude.doubleValue(),
			longitude.doubleValue(),
			menu.storeLatitude().doubleValue(),
			menu.storeLongitude().doubleValue()
		);
	}
}
