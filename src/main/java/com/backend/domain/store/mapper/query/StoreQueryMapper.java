package com.backend.domain.store.mapper.query;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.backend.domain.store.entity.Store;

@Mapper
public interface StoreQueryMapper {

	/**
	 * Bounding Box 내의 매장 목록을 조회합니다.
	 * Haversine 공식은 Service 레이어에서 처리하므로, Mapper는 Bounding Box 필터링 및 카테고리/키워드 필터링을 수행합니다.
	 *
	 * @param minLatitude 최소 위도
	 * @param maxLatitude 최대 위도
	 * @param minLongitude 최소 경도
	 * @param maxLongitude 최대 경도
	 * @param categories 카테고리 리스트
	 * @param keyword 검색 키워드 (매장명/주소)
	 * @return Bounding Box 내의 매장 리스트
	 */
	List<Store> findStoresWithinBounds(
		@Param("minLatitude") BigDecimal minLatitude,
		@Param("maxLatitude") BigDecimal maxLatitude,
		@Param("minLongitude") BigDecimal minLongitude,
		@Param("maxLongitude") BigDecimal maxLongitude,
		@Param("categories") List<String> categories,
		@Param("keyword") String keyword
	);
}
