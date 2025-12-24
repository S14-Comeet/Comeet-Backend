package com.backend.domain.review.dto.request;

import java.math.BigDecimal;

import com.backend.domain.bean.enums.RoastingLevel;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "커핑 노트 작성 요청 DTO")
public record CuppingNoteReqDto(
	@Schema(description = "로스팅 레벨 (LIGHT, MEDIUM_LIGHT, MEDIUM, MEDIUM_DARK, DARK)", example = "MEDIUM", requiredMode = RequiredMode.REQUIRED)
	@NotNull(message = "로스팅 레벨은 필수 입력값입니다.")
	RoastingLevel roastLevel,

	@Schema(description = "Fragrance 점수 (0.00 ~ 15.00)", example = "8.50", requiredMode = RequiredMode.REQUIRED)
	@NotNull(message = "Fragrance 점수는 필수 입력값입니다.")
	@DecimalMin(value = "0.00", message = "Fragrance 점수는 0.00 이상이어야 합니다.")
	@DecimalMax(value = "15.00", message = "Fragrance 점수는 15.00 이하여야 합니다.")
	@Digits(integer = 2, fraction = 2, message = "Fragrance 점수는 소수점 둘째 자리까지 입력 가능합니다.")
	BigDecimal fragranceScore,

	@Schema(description = "Aroma 점수 (0.00 ~ 15.00)", example = "8.00", requiredMode = RequiredMode.REQUIRED)
	@NotNull(message = "Aroma 점수는 필수 입력값입니다.")
	@DecimalMin(value = "0.00", message = "Aroma 점수는 0.00 이상이어야 합니다.")
	@DecimalMax(value = "15.00", message = "Aroma 점수는 15.00 이하여야 합니다.")
	@Digits(integer = 2, fraction = 2, message = "Aroma 점수는 소수점 둘째 자리까지 입력 가능합니다.")
	BigDecimal aromaScore,

	@Schema(description = "Flavor 점수 (0.00 ~ 15.00)", example = "9.00", requiredMode = RequiredMode.REQUIRED)
	@NotNull(message = "Flavor 점수는 필수 입력값입니다.")
	@DecimalMin(value = "0.00", message = "Flavor 점수는 0.00 이상이어야 합니다.")
	@DecimalMax(value = "15.00", message = "Flavor 점수는 15.00 이하여야 합니다.")
	@Digits(integer = 2, fraction = 2, message = "Flavor 점수는 소수점 둘째 자리까지 입력 가능합니다.")
	BigDecimal flavorScore,

	@Schema(description = "Aftertaste 점수 (0.00 ~ 15.00)", example = "8.75", requiredMode = RequiredMode.REQUIRED)
	@NotNull(message = "Aftertaste 점수는 필수 입력값입니다.")
	@DecimalMin(value = "0.00", message = "Aftertaste 점수는 0.00 이상이어야 합니다.")
	@DecimalMax(value = "15.00", message = "Aftertaste 점수는 15.00 이하여야 합니다.")
	@Digits(integer = 2, fraction = 2, message = "Aftertaste 점수는 소수점 둘째 자리까지 입력 가능합니다.")
	BigDecimal aftertasteScore,

	@Schema(description = "Acidity 점수 (0.00 ~ 15.00)", example = "8.25", requiredMode = RequiredMode.REQUIRED)
	@NotNull(message = "Acidity 점수는 필수 입력값입니다.")
	@DecimalMin(value = "0.00", message = "Acidity 점수는 0.00 이상이어야 합니다.")
	@DecimalMax(value = "15.00", message = "Acidity 점수는 15.00 이하여야 합니다.")
	@Digits(integer = 2, fraction = 2, message = "Acidity 점수는 소수점 둘째 자리까지 입력 가능합니다.")
	BigDecimal acidityScore,

	@Schema(description = "Sweetness 점수 (0.00 ~ 15.00)", example = "9.25", requiredMode = RequiredMode.REQUIRED)
	@NotNull(message = "Sweetness 점수는 필수 입력값입니다.")
	@DecimalMin(value = "0.00", message = "Sweetness 점수는 0.00 이상이어야 합니다.")
	@DecimalMax(value = "15.00", message = "Sweetness 점수는 15.00 이하여야 합니다.")
	@Digits(integer = 2, fraction = 2, message = "Sweetness 점수는 소수점 둘째 자리까지 입력 가능합니다.")
	BigDecimal sweetnessScore,

	@Schema(description = "Mouthfeel 점수 (0.00 ~ 15.00)", example = "8.50", requiredMode = RequiredMode.REQUIRED)
	@NotNull(message = "Mouthfeel 점수는 필수 입력값입니다.")
	@DecimalMin(value = "0.00", message = "Mouthfeel 점수는 0.00 이상이어야 합니다.")
	@DecimalMax(value = "15.00", message = "Mouthfeel 점수는 15.00 이하여야 합니다.")
	@Digits(integer = 2, fraction = 2, message = "Mouthfeel 점수는 소수점 둘째 자리까지 입력 가능합니다.")
	BigDecimal mouthfeelScore,

	@Schema(description = "Fragrance/Aroma 상세 설명", example = "꽃향기와 베리류의 향이 강하게 느껴짐", requiredMode = RequiredMode.NOT_REQUIRED)
	@Size(max = 1000, message = "Fragrance/Aroma 상세 설명은 최대 1000자까지 입력 가능합니다.")
	String fragranceAromaDetail,

	@Schema(description = "Flavor/Aftertaste 상세 설명", example = "블루베리와 다크 초콜릿의 풍미가 입안에 오래 지속됨", requiredMode = RequiredMode.NOT_REQUIRED)
	@Size(max = 1000, message = "Flavor/Aftertaste 상세 설명은 최대 1000자까지 입력 가능합니다.")
	String flavorAftertasteDetail,

	@Schema(description = "Acidity 노트", example = "밝고 깨끗한 산미, 레몬과 사과의 느낌", requiredMode = RequiredMode.NOT_REQUIRED)
	@Size(max = 500, message = "Acidity 노트는 최대 500자까지 입력 가능합니다.")
	String acidityNotes,

	@Schema(description = "Sweetness 노트", example = "캐러멜과 꿀의 단맛이 균형있게 느껴짐", requiredMode = RequiredMode.NOT_REQUIRED)
	@Size(max = 500, message = "Sweetness 노트는 최대 500자까지 입력 가능합니다.")
	String sweetnessNotes,

	@Schema(description = "Mouthfeel 노트", example = "크리미하고 부드러운 바디감", requiredMode = RequiredMode.NOT_REQUIRED)
	@Size(max = 500, message = "Mouthfeel 노트는 최대 500자까지 입력 가능합니다.")
	String mouthfeelNotes,

	@Schema(description = "전체 평가", example = "균형잡힌 산미와 단맛이 인상적인 스페셜티 커피", requiredMode = RequiredMode.NOT_REQUIRED)
	@Size(max = 1000, message = "전체 평가는 최대 1000자까지 입력 가능합니다.")
	String overallNotes
) {
}
