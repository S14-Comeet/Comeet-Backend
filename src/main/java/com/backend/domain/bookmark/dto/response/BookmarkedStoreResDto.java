package com.backend.domain.bookmark.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "북마크된 카페 정보 응답 DTO")
public record BookmarkedStoreResDto(
	@Schema(description = "카페 ID", example = "123")
	Long storeId,

	@Schema(description = "카페명", example = "카페 라떼")
	String name,

	@Schema(description = "카페 설명", example = "핸드드립 전문 카페")
	String description,

	@Schema(description = "주소", example = "서울 강남구 테헤란로 123")
	String address,

	@Schema(description = "위도", example = "37.5012")
	BigDecimal latitude,

	@Schema(description = "경도", example = "127.0396")
	BigDecimal longitude,

	@Schema(description = "카페 카테고리", example = "HAND_DRIP")
	String category,

	@Schema(description = "평균 평점", example = "4.5")
	BigDecimal averageRating,

	@Schema(description = "리뷰 수", example = "28")
	Integer reviewCount,

	@Schema(description = "썸네일 이미지 URL", example = "https://example.com/cafe-latte.jpg")
	String thumbnailUrl,

	@Schema(description = "폐업 여부", example = "false")
	boolean isClosed,

	@Schema(description = "폴더에 추가된 일시")
	LocalDateTime addedAt
) {
}
