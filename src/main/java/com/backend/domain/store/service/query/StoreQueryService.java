package com.backend.domain.store.service.query;

import java.math.BigDecimal;

import com.backend.domain.store.dto.request.StoreSearchReqDto;
import com.backend.domain.store.dto.response.StoreListResDto;

public interface StoreQueryService {

	/**
	 * 사용자 위치 기반으로 지정된 거리 내의 매장 목록을 조회합니다.
	 * Bounding Box + Haversine 방식으로 거리 필터링을 수행하며,
	 * 카테고리 및 키워드 필터링을 지원합니다.
	 *
	 * @param request 가맹점 검색 요청 DTO
	 * @return 거리 내 매장 목록 (마커 정보 포함)
	 */
	StoreListResDto searchStores(StoreSearchReqDto request);

	/**
	 * 특정 매장이 주어진 좌표로부터 지정된 거리 내에 있는지 확인합니다.
	 *
	 * @param storeId 매장 ID
	 * @param latitude 확인할 위치의 위도
	 * @param longitude 확인할 위치의 경도
	 * @param distanceInMeters 기준 거리 (미터)
	 * @return 거리 내에 있으면 true, 초과하면 false
	 */
	boolean isStoreWithinDistance(String storeId, BigDecimal latitude, BigDecimal longitude,
		double distanceInMeters);
}
