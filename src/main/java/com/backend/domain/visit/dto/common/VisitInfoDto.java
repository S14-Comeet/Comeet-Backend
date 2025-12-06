package com.backend.domain.visit.dto.common;

import java.time.LocalDateTime;

public record VisitInfoDto(
	Long verificationId,
	Long menuId, //TODO 인증 내역에서 메뉴 정보도 같이 포함할것인지는 생각해야한다.
	Boolean verified,
	LocalDateTime visitedAt
) {
}