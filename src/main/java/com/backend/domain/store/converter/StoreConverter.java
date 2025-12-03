package com.backend.domain.store.converter;

import java.util.List;
import java.util.Map;

import com.backend.common.util.GeoUtils;
import com.backend.domain.store.dto.response.StoreResDto;
import com.backend.domain.store.dto.response.StoreListResDto;
import com.backend.domain.store.entity.Store;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StoreConverter {

	// === 응답 DTO 변환 메서드 ===

	/**
	 * Store Entity와 거리 정보를 StoreResDto로 변환
	 * @param store 매장 엔티티
	 * @param distanceKm 사용자로부터의 거리 (km)
	 * @return 가맹점 정보 응답 DTO
	 */
	public static StoreResDto toStoreResponse(final Store store, final Double distanceKm) {
		return StoreResDto.builder()
			.storeId(store.getStoreId())
			.name(store.getName())
			.description(store.getDescription())
			.address(store.getAddress())
			.latitude(store.getLatitude())
			.longitude(store.getLongitude())
			.category(store.getCategory())
			.averageRating(store.getAverageRating())
			.reviewCount(store.getReviewCount())
			.thumbnailUrl(store.getThumbnailUrl())
			.distance(GeoUtils.convertKmToMeters(distanceKm))
			.isClosed(store.isClosed())
			.markerColor(determineMarkerColor(store.isClosed()))
			.build();
	}

	/**
	 * 매장의 영업 상태에 따라 마커 색상 결정
	 * @param isClosed 영업 중지 여부
	 * @return 마커 색상 (영업 중: BLUE, 영업 안함: RED)
	 */
	private static String determineMarkerColor(final Boolean isClosed) {
		return (isClosed != null && isClosed) ? "RED" : "BLUE";
	}

	/**
	 * Store 리스트와 거리 맵을 StoreListResDto로 변환
	 * @param stores 매장 엔티티 리스트
	 * @param distanceMap 매장별 거리 정보 (storeId -> distance in km)
	 * @return 가맹점 목록 응답 DTO
	 */
	public static StoreListResDto toStoreListResponse(final List<Store> stores,
		final Map<String, Double> distanceMap) {
		final List<StoreResDto> storeList = stores.stream()
			.map(store -> toStoreResponse(store, distanceMap.get(store.getStoreId())))
			.toList();

		return StoreListResDto.builder()
			.totalCount(storeList.size())
			.stores(storeList)
			.build();
	}
}
