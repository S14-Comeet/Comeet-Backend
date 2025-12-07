package com.backend.domain.visit.dto.common;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record VisitInfoDto(
	Long visitId,
	Long menuId, //TODO 인증 내역에서 메뉴 정보도 같이 포함할것인지는 생각해야한다.
	Double latitude,
	Double longitude,
	Boolean verified,
	LocalDateTime visitedAt
) {
}