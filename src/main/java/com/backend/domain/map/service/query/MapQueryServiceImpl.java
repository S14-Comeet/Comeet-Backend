package com.backend.domain.map.service.query;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.backend.domain.map.converter.MapConverter;
import com.backend.domain.map.dto.response.MapMarkersResDto;
import com.backend.domain.map.entity.Store;
import com.backend.domain.map.mapper.query.MapQueryMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MapQueryServiceImpl implements MapQueryService {
	private static final double EARTH_RADIUS_KM = 6371.0;
	private static final double LATITUDE_DEGREE_KM = 111.0;
	private static final double DEFAULT_MAX_DISTANCE_KM = 1.0;

	private final MapQueryMapper queryMapper;

	@Override
	public MapMarkersResDto getStoresWithinDistance(final BigDecimal latitude, final BigDecimal longitude,
		final Double maxDistance) {
		final double distance = (maxDistance != null) ? maxDistance : DEFAULT_MAX_DISTANCE_KM;

		// 1. Bounding Box 경계 계산
		final BoundingBox boundingBox = calculateBoundingBox(latitude, longitude, distance);

		// 2. Bounding Box 내의 매장 조회 (1차 필터링)
		final List<Store> candidateStores = queryMapper.findStoresWithinBounds(
			boundingBox.minLatitude(),
			boundingBox.maxLatitude(),
			boundingBox.minLongitude(),
			boundingBox.maxLongitude()
		);

		// 3. Haversine 공식으로 정확한 거리 계산 및 필터링
		final Map<String, Double> distanceMap = new HashMap<>();
		final List<Store> filteredStores = candidateStores.stream()
			.filter(store -> {
				final double storeDistance = calculateHaversineDistance(
					latitude.doubleValue(),
					longitude.doubleValue(),
					store.getLatitude().doubleValue(),
					store.getLongitude().doubleValue()
				);
				if (storeDistance <= distance) {
					distanceMap.put(store.getStoreId(), storeDistance);
					return true;
				}
				return false;
			})
			.sorted((s1, s2) -> Double.compare(
				distanceMap.get(s1.getStoreId()),
				distanceMap.get(s2.getStoreId())
			))
			.toList();

		// 4. 응답 DTO 변환
		return MapConverter.toMarkersResponse(filteredStores, distanceMap);
	}

	/**
	 * 주어진 위치와 거리를 기준으로 Bounding Box 계산
	 *
	 * @param latitude    중심 위도
	 * @param longitude   중심 경도
	 * @param distanceKm  거리 (km)
	 * @return Bounding Box 좌표
	 */
	private BoundingBox calculateBoundingBox(final BigDecimal latitude, final BigDecimal longitude,
		final double distanceKm) {
		final double lat = latitude.doubleValue();
		final double lon = longitude.doubleValue();

		// 위도 변화량 계산 (위도 1도 ≈ 111km)
		final double deltaLat = distanceKm / LATITUDE_DEGREE_KM;

		// 경도 변화량 계산 (경도 1도 ≈ 111km * cos(latitude))
		final double deltaLon = distanceKm / (LATITUDE_DEGREE_KM * Math.cos(Math.toRadians(lat)));

		return new BoundingBox(
			BigDecimal.valueOf(lat - deltaLat),
			BigDecimal.valueOf(lat + deltaLat),
			BigDecimal.valueOf(lon - deltaLon),
			BigDecimal.valueOf(lon + deltaLon)
		);
	}

	/**
	 * Haversine 공식을 사용하여 두 지점 간의 거리 계산
	 *
	 * @param lat1 첫 번째 지점의 위도
	 * @param lon1 첫 번째 지점의 경도
	 * @param lat2 두 번째 지점의 위도
	 * @param lon2 두 번째 지점의 경도
	 * @return 거리 (km)
	 */
	private double calculateHaversineDistance(final double lat1, final double lon1, final double lat2,
		final double lon2) {
		final double lat1Rad = Math.toRadians(lat1);
		final double lat2Rad = Math.toRadians(lat2);
		final double deltaLatRad = Math.toRadians(lat2 - lat1);
		final double deltaLonRad = Math.toRadians(lon2 - lon1);

		final double a = Math.sin(deltaLatRad / 2) * Math.sin(deltaLatRad / 2)
			+ Math.cos(lat1Rad) * Math.cos(lat2Rad)
			* Math.sin(deltaLonRad / 2) * Math.sin(deltaLonRad / 2);

		final double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return EARTH_RADIUS_KM * c;
	}

	/**
	 * Bounding Box 좌표를 담는 레코드
	 */
	private record BoundingBox(
		BigDecimal minLatitude,
		BigDecimal maxLatitude,
		BigDecimal minLongitude,
		BigDecimal maxLongitude
	) {
	}
}
