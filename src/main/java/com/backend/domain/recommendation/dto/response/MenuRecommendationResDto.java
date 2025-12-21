package com.backend.domain.recommendation.dto.response;

import java.math.BigDecimal;
import java.util.List;

import com.backend.domain.bean.dto.common.BeanBadgeDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "메뉴 추천 응답 DTO")
public record MenuRecommendationResDto(
	@Schema(description = "메뉴 ID", example = "1")
	Long menuId,

	@Schema(description = "메뉴 이름", example = "에티오피아 핸드드립")
	String menuName,

	@Schema(description = "메뉴 설명", example = "에티오피아 예가체프 원두로 내린 핸드드립 커피")
	String menuDescription,

	@Schema(description = "가격", example = "6000")
	Integer price,

	@Schema(description = "메뉴 이미지 URL", example = "https://example.com/menu.jpg")
	String menuImageUrl,

	// 카페 정보
	@Schema(description = "카페 ID", example = "1")
	Long storeId,

	@Schema(description = "카페 이름", example = "블루보틀 강남점")
	String storeName,

	@Schema(description = "카페 주소", example = "서울특별시 강남구 테헤란로 152")
	String storeAddress,

	@Schema(description = "카페 위도", example = "37.4995")
	BigDecimal storeLatitude,

	@Schema(description = "카페 경도", example = "127.0288")
	BigDecimal storeLongitude,

	@Schema(description = "사용자와의 거리 (km, LOCAL 모드 시)", example = "1.2")
	Double distanceKm,

	// 원두 정보
	@Schema(description = "사용된 원두 목록")
	List<BeanBadgeDto> beans,

	// 추천 정보
	@Schema(description = "추천 순위 (1-3)", example = "1")
	Integer rank,

	@Schema(description = "추천 이유", example = "선호하시는 산미 특성이 잘 나타나는 메뉴입니다.")
	String reason,

	@Schema(description = "유사도 점수 (0.0 ~ 1.0)", example = "0.89")
	Double similarityScore
) {
}
