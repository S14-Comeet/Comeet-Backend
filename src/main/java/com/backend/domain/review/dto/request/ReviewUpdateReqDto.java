package com.backend.domain.review.dto.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "리뷰 수정 요청 DTO")
public record ReviewUpdateReqDto(
	@Schema(description = "리뷰 내용", example = "산미가 강하고 과일향이 풍부한 원두였습니다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	String content,

	@Schema(description = "공개 여부", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull(message = "공개 여부는 필수 입력값입니다.")
	Boolean isPublic,

	@Schema(description = "리뷰 이미지 URL", example = "https://example.com/image.jpg", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	String imageUrl,

	@Schema(description = "Flavor 뱃지 ID 목록", example = "[1, 2, 3]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	List<Long> flavorIdList
) {
}