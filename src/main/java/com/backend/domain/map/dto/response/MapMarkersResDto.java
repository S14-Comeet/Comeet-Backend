package com.backend.domain.map.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "지도 마커 목록 응답 DTO")
public record MapMarkersResDto(
	@Schema(description = "검색된 매장 수", example = "10")
	Integer totalCount,

	@Schema(description = "매장 마커 목록")
	List<MapMarkerResDto> markers
) {
}
