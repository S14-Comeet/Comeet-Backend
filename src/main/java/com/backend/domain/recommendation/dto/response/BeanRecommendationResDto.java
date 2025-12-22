package com.backend.domain.recommendation.dto.response;

import java.util.List;

import com.backend.domain.bean.enums.RoastingLevel;
import com.backend.domain.flavor.dto.common.FlavorBadgeDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "원두 추천 응답 DTO")
public record BeanRecommendationResDto(
	@Schema(description = "원두 ID", example = "1")
	Long beanId,

	@Schema(description = "원두 이름", example = "에티오피아 예가체프")
	String beanName,

	@Schema(description = "원두 설명", example = "플로럴 향과 시트러스 노트가 특징인 원두")
	String description,

	@Schema(description = "원산지", example = "에티오피아")
	String origin,

	@Schema(description = "로스팅 레벨", example = "LIGHT")
	RoastingLevel roastLevel,

	@Schema(description = "플레이버 상세 목록")
	List<FlavorBadgeDto> flavors,

	@Schema(description = "총 점수", example = "85")
	Integer totalScore,

	@Schema(description = "추천 순위 (1-3)", example = "1")
	Integer rank,

	@Schema(description = "추천 이유", example = "밝은 산미와 플로럴 향을 선호하시는 취향에 딱 맞는 원두입니다.")
	String reason,

	@Schema(description = "유사도 점수 (0.0 ~ 1.0)", example = "0.92")
	Double similarityScore
) {
}
