package com.backend.domain.store.service.query;

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
}
