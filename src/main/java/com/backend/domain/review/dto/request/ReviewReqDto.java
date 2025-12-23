package com.backend.domain.review.dto.request;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Schema(description = "리뷰 작성/수정 요청 DTO")
public record ReviewReqDto(
	@Schema(description = "방문 인증 ID (작성 시 필수, 수정 시 무시됨)", example = "1", requiredMode = RequiredMode.NOT_REQUIRED)
	Long visitId,

	@Schema(description = "메뉴 ID (작성 시 필수, 수정 시 무시됨)", example = "1", requiredMode = RequiredMode.NOT_REQUIRED)
	Long menuId,

	@Schema(description = "가맹점 ID (작성 시 필수, 수정 시 무시됨)", example = "1", requiredMode = RequiredMode.NOT_REQUIRED)
	Long storeId,

	@Schema(description = "리뷰 내용", example = "산미가 강하고 과일향이 풍부한 원두였습니다.", requiredMode = RequiredMode.NOT_REQUIRED)
	String content,

	@Schema(description = "공개 여부", example = "true", requiredMode = RequiredMode.REQUIRED)
	@NotNull(message = "공개 여부는 필수 입력값입니다.")
	Boolean isPublic,

	@Schema(description = "리뷰 이미지 URL", example = "https://example.com/image.jpg", requiredMode = RequiredMode.NOT_REQUIRED)
	String imageUrl,

	@Schema(description = "평점 (0.5 ~ 5.0, 0.5 단위)", example = "4.5", requiredMode = RequiredMode.NOT_REQUIRED)
	@DecimalMin(value = "0.5", message = "평점은 0.5 이상이어야 합니다.")
	@DecimalMax(value = "5.0", message = "평점은 5.0 이하여야 합니다.")
	BigDecimal rating,

	@Schema(description = "Flavor 뱃지 ID 목록", example = "[1, 2, 3]", requiredMode = RequiredMode.NOT_REQUIRED)
	List<Long> flavorIdList
) {
}
