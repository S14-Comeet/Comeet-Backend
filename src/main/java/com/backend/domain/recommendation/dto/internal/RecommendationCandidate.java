package com.backend.domain.recommendation.dto.internal;

import java.math.BigDecimal;
import java.util.List;

import com.backend.domain.bean.enums.RoastingLevel;

import lombok.Builder;

/**
 * 추천 후보 정보 (내부 사용)
 */
@Builder
public record RecommendationCandidate(
	// 원두 정보
	Long beanId,
	String beanName,
	String beanDescription,
	String beanOrigin,
	RoastingLevel roastLevel,
	List<String> flavorTags,
	Integer totalScore,

	// 메뉴 정보 (메뉴 추천 시)
	Long menuId,
	String menuName,
	String menuDescription,
	Integer menuPrice,
	String menuImageUrl,

	// 카페 정보 (메뉴 추천 시)
	Long storeId,
	String storeName,
	String storeAddress,
	BigDecimal storeLatitude,
	BigDecimal storeLongitude,

	// 유사도 점수
	Double similarityScore,

	// 거리 (LOCAL 모드 시)
	Double distanceKm
) {
	/**
	 * LLM 리랭킹용 텍스트 생성
	 */
	public String toDescriptionForLlm() {
		StringBuilder sb = new StringBuilder();
		sb.append("원두: ").append(beanName);

		if (beanOrigin != null) {
			sb.append(" (").append(beanOrigin).append(")");
		}

		if (roastLevel != null) {
			sb.append(", 로스팅: ").append(roastLevel.name());
		}

		if (flavorTags != null && !flavorTags.isEmpty()) {
			sb.append(", 특징: ").append(String.join(", ", flavorTags));
		}

		if (menuName != null) {
			sb.append(" | 메뉴: ").append(menuName);
			if (menuPrice != null) {
				sb.append(" (").append(menuPrice).append("원)");
			}
		}

		if (storeName != null) {
			sb.append(" @ ").append(storeName);
			if (distanceKm != null) {
				sb.append(String.format(" (%.1fkm)", distanceKm));
			}
		}

		return sb.toString();
	}
}
