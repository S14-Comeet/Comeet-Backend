package com.backend.common.util;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GeoUtils {

	private static final double EARTH_RADIUS_KM = 6371.0;
	private static final double LATITUDE_DEGREE_KM = 111.0;
	private static final int DEFAULT_RADIUS_METERS = 1000;

	/**
	 * 미터 단위 반경을 킬로미터로 변환 (기본값 처리 포함)
	 * @param radiusInMeters 검색 반경 (m)
	 * @return 검색 반경 (km)
	 */
	public static Double getRadiusInKm(final Integer radiusInMeters) {
		int radius = (radiusInMeters != null) ? radiusInMeters : DEFAULT_RADIUS_METERS;
		return radius / 1000.0;
	}

	/**
	 * km를 미터로 변환
	 * @param distanceKm 거리 (km)
	 * @return 거리 (m)
	 */
	public static Double convertKmToMeters(final Double distanceKm) {
		return distanceKm * 1000.0;
	}

	/**
	 * 주어진 위치와 거리를 기준으로 Bounding Box 계산
	 *
	 * @param latitude    중심 위도
	 * @param longitude   중심 경도
	 * @param distanceKm  거리 (km)
	 * @return Bounding Box 좌표
	 */
	public static BoundingBox calculateBoundingBox(final BigDecimal latitude, final BigDecimal longitude,
		final double distanceKm) {
		double lat = latitude.doubleValue();
		double lon = longitude.doubleValue();

		// 위도 변화량 계산 (위도 1도 ≈ 111km)
		double deltaLat = distanceKm / LATITUDE_DEGREE_KM;

		// 경도 변화량 계산 (경도 1도 ≈ 111km * cos(latitude))
		double deltaLon = distanceKm / (LATITUDE_DEGREE_KM * Math.cos(Math.toRadians(lat)));

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
	public static double calculateHaversineDistance(final double lat1, final double lon1, final double lat2,
		final double lon2) {
		double lat1Rad = Math.toRadians(lat1);
		double lat2Rad = Math.toRadians(lat2);
		double deltaLatRad = Math.toRadians(lat2 - lat1);
		double deltaLonRad = Math.toRadians(lon2 - lon1);

		double a = Math.sin(deltaLatRad / 2) * Math.sin(deltaLatRad / 2)
			+ Math.cos(lat1Rad) * Math.cos(lat2Rad)
			* Math.sin(deltaLonRad / 2) * Math.sin(deltaLonRad / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return EARTH_RADIUS_KM * c;
	}

	/**
	 * Bounding Box 좌표를 담는 레코드
	 */
	public record BoundingBox(
		BigDecimal minLatitude,
		BigDecimal maxLatitude,
		BigDecimal minLongitude,
		BigDecimal maxLongitude
	) {
	}
}
