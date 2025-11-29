package com.backend.domain.map.service.query;

import com.backend.domain.map.dto.request.MapBoundsReqDto;
import com.backend.domain.map.dto.response.MapMarkersResDto;

public interface MapQueryService {

	/**
	 * 사용자 위치 기반으로 지정된 거리 내의 매장 목록을 조회합니다.
	 * Bounding Box + Haversine 방식으로 거리 필터링을 수행합니다.
	 *
	 * @param reqDto 사용자 위치 및 최대 거리 정보
	 * @return 거리 내 매장 마커 목록
	 */
	MapMarkersResDto getStoresWithinDistance(MapBoundsReqDto reqDto);
}
