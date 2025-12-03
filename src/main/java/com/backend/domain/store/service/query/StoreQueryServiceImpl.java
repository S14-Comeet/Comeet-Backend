package com.backend.domain.store.service.query;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.backend.common.util.GeoUtils;
import com.backend.common.util.StringUtils;
import com.backend.domain.store.converter.StoreConverter;
import com.backend.domain.store.dto.request.StoreSearchReqDto;
import com.backend.domain.store.dto.response.StoreListResDto;
import com.backend.domain.store.entity.Store;
import com.backend.domain.store.mapper.query.StoreQueryMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreQueryServiceImpl implements StoreQueryService {

	private final StoreQueryMapper queryMapper;

	@Override
	public StoreListResDto searchStores(final StoreSearchReqDto request) {
		double distanceKm = GeoUtils.getRadiusInKm(request.radius());

		// 1. Bounding Box 경계 계산
		GeoUtils.BoundingBox boundingBox = GeoUtils.calculateBoundingBox(
			request.latitude(), request.longitude(), distanceKm);

		// 2. Bounding Box 내의 매장 조회 (1차 필터링 + 카테고리/키워드 필터링)
		List<Store> candidateStores = queryMapper.findStoresWithinBounds(
			boundingBox.minLatitude(),
			boundingBox.maxLatitude(),
			boundingBox.minLongitude(),
			boundingBox.maxLongitude(),
			StringUtils.parseCategoryList(request.categories()),
			request.keyword()
		);

		// 3. Haversine 공식으로 정확한 거리 계산 및 필터링 후 거리순 정렬
		Map<String, Double> distanceMap = new HashMap<>();
		List<Store> filteredStores = candidateStores.stream()
			.filter(store -> {
				double storeDistance = GeoUtils.calculateHaversineDistance(
					request.latitude().doubleValue(),
					request.longitude().doubleValue(),
					store.getLatitude().doubleValue(),
					store.getLongitude().doubleValue()
				);
				if (storeDistance <= distanceKm) {
					distanceMap.put(store.getStoreId(), storeDistance);
					return true;
				}
				return false;
			})
			.sorted(Comparator.comparingDouble(store -> distanceMap.get(store.getStoreId())))
			.toList();

		// 4. 응답 DTO 변환 (항상 마커 정보 포함)
		return StoreConverter.toStoreListResponse(filteredStores, distanceMap);
	}

	@Override
	public boolean isStoreWithinDistance(final String storeId, final BigDecimal latitude,
		final BigDecimal longitude, final double distanceInMeters) {
		Store store = queryMapper.findById(storeId);
        // 추후 커스텀 예외처리 고려해볼만 함.
        if (store == null) {
			return false;
		}

		double distanceKm = GeoUtils.calculateHaversineDistance(
			latitude.doubleValue(),
			longitude.doubleValue(),
			store.getLatitude().doubleValue(),
			store.getLongitude().doubleValue()
		);

		double actualDistanceMeters = GeoUtils.convertKmToMeters(distanceKm);

		return actualDistanceMeters <= distanceInMeters;
	}
}
