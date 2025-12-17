package com.backend.domain.menu.dto.response;

import com.backend.domain.menu.enums.Category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "메뉴 응답 DTO")
public record MenuResDto(
	@Schema(description = "메뉴 ID", example = "1")
	Long id,

	@Schema(description = "가맹점 ID", example = "1")
	Long storeId,

	@Schema(description = "메뉴 이름", example = "아메리카노")
	String name,

	@Schema(description = "메뉴 설명", example = "에티오피아 예가체프 원두로 만든 아메리카노")
	String description,

	@Schema(description = "가격", example = "5000")
	Integer price,

	@Schema(description = "카테고리", example = "COFFEE")
	Category category,

	@Schema(description = "이미지 URL", example = "https://example.com/menu.jpg")
	String imageUrl
) {
}
