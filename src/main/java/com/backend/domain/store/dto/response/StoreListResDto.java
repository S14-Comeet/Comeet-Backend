package com.backend.domain.store.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "가맹점 목록 응답 DTO")
public record StoreListResDto(
	@Schema(description = "검색 조건에 일치하는 총 매장 수", example = "10")
	Integer totalCount,

	@Schema(description = "매장 목록 (거리순 정렬, 마커 정보 포함)")
	List<StoreResDto> stores
) {
}
