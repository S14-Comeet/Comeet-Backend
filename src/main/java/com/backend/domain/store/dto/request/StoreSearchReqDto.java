package com.backend.domain.store.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "가맹점 검색 요청 DTO")
public record StoreSearchReqDto(
	@Schema(
		description = "중심 위도 (-90 ~ 90)",
		example = "37.5665",
		requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotNull
	@DecimalMin("-90")
	@DecimalMax("90")
	BigDecimal latitude,

	@Schema(
		description = "중심 경도 (-180 ~ 180)",
		example = "126.9780",
		requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotNull
	@DecimalMin("-180")
	@DecimalMax("180")
	BigDecimal longitude,

	@Schema(
		description = "검색 반경 (미터 단위, 미입력 시 1000m 기본값 적용)",
		example = "10000",
		requiredMode = Schema.RequiredMode.NOT_REQUIRED
	)
	@Positive
	Integer radius,

	@Schema(
		description = "카테고리 필터 (콤마로 구분하여 복수 선택 가능, 부분 문자열 매칭)",
		example = "라떼, 드립",
		requiredMode = Schema.RequiredMode.NOT_REQUIRED
	)
	String categories,

	@Schema(
		description = "매장명 또는 주소 검색 키워드 (부분 일치 검색)",
		example = "강남",
		requiredMode = Schema.RequiredMode.NOT_REQUIRED
	)
	String keyword
) {
}
