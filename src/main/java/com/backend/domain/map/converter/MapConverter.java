package com.backend.domain.map.converter;

import java.util.List;
import java.util.Map;

import com.backend.domain.map.dto.response.MapMarkerResDto;
import com.backend.domain.map.dto.response.MapMarkersResDto;
import com.backend.domain.map.entity.Store;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MapConverter {

	/**
	 * Store Entity와 거리 정보를 MapMarkerResDto로 변환
	 * @param store 매장 엔티티
//	 * @param distance 사용자로부터의 거리 (km)
	 * @return 지도 마커 응답 DTO
	 */
	public static MapMarkerResDto toMarkerResponse(final Store store, final Double distance) {
		return MapMarkerResDto.builder()
			.storeId(store.getStoreId())
			.name(store.getName())
			.latitude(store.getLatitude())
			.longitude(store.getLongitude())
			.category(store.getCategory())
			.averageRating(store.getAverageRating())
			.thumbnailUrl(store.getThumbnailUrl())
			.distance(distance)
			.build();
	}

	/**
	 * Store 리스트와 거리 맵을 MapMarkersResDto로 변환
	 * @param stores 매장 엔티티 리스트
	 * @param distanceMap 매장별 거리 정보 (storeId -> distance)
	 * @return 지도 마커 목록 응답 DTO
	 */
	public static MapMarkersResDto toMarkersResponse(final List<Store> stores,
		final Map<String, Double> distanceMap) {
		final List<MapMarkerResDto> markers = stores.stream()
			.map(store -> toMarkerResponse(store, distanceMap.get(store.getStoreId())))
			.toList();

		return MapMarkersResDto.builder()
			.totalCount(markers.size())
			.markers(markers)
			.build();
	}
}
