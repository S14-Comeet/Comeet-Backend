package com.backend.domain.map.mapper.query;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.backend.domain.map.entity.Store;

@Mapper
public interface MapQueryMapper {

	/**
	 * Bounding Box 내의 매장 목록을 조회합니다.
	 * Haversine 공식은 Service 레이어에서 처리하므로, Mapper는 Bounding Box 필터링만 수행합니다.
	 *
	 * @param minLatitude 최소 위도
	 * @param maxLatitude 최대 위도
	 * @param minLongitude 최소 경도
	 * @param maxLongitude 최대 경도
	 * @return Bounding Box 내의 매장 리스트
	 */
	List<Store> findStoresWithinBounds(
		@Param("minLatitude") BigDecimal minLatitude,
		@Param("maxLatitude") BigDecimal maxLatitude,
		@Param("minLongitude") BigDecimal minLongitude,
		@Param("maxLongitude") BigDecimal maxLongitude
	);
}
