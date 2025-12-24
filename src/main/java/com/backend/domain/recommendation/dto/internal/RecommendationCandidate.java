package com.backend.domain.recommendation.dto.internal;

import java.math.BigDecimal;
import java.util.List;

import com.backend.domain.bean.enums.RoastingLevel;

import lombok.Builder;

@Builder
public record RecommendationCandidate(
	Long beanId,
	String beanName,
	String beanDescription,
	String beanOrigin,
	RoastingLevel roastLevel,
	List<String> flavorTags,
	Integer totalScore,

	Long menuId,
	String menuName,
	String menuDescription,
	Integer menuPrice,
	String menuImageUrl,

	Long storeId,
	String storeName,
	String storeAddress,
	BigDecimal storeLatitude,
	BigDecimal storeLongitude,

	Double similarityScore,

	Double distanceKm) {
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
