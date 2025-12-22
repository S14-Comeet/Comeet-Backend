package com.backend.domain.beanscore.entity;

import java.time.LocalDateTime;

import com.backend.domain.bean.enums.RoastingLevel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 원두 점수 엔티티
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanScore {

	private Long id;
	private Long beanId;
	private Integer acidity;
	private Integer body;
	private Integer sweetness;
	private Integer bitterness;
	private Integer aroma;
	private Integer flavor;
	private Integer aftertaste;
	private Integer totalScore;
	private RoastingLevel roastLevel;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	/**
	 * 기본 점수로 생성 (모든 값 5)
	 */
	public static BeanScore createDefault(Long beanId) {
		return BeanScore.builder()
			.beanId(beanId)
			.acidity(5)
			.body(5)
			.sweetness(5)
			.bitterness(5)
			.aroma(5)
			.flavor(5)
			.aftertaste(5)
			.totalScore(0)
			.roastLevel(RoastingLevel.MEDIUM)
			.build();
	}

	/**
	 * 점수 업데이트
	 */
	public void update(
		Integer acidity,
		Integer body,
		Integer sweetness,
		Integer bitterness,
		Integer aroma,
		Integer flavor,
		Integer aftertaste,
		Integer totalScore,
		RoastingLevel roastLevel
	) {
		if (acidity != null) {
			this.acidity = acidity;
		}
		if (body != null) {
			this.body = body;
		}
		if (sweetness != null) {
			this.sweetness = sweetness;
		}
		if (bitterness != null) {
			this.bitterness = bitterness;
		}
		if (aroma != null) {
			this.aroma = aroma;
		}
		if (flavor != null) {
			this.flavor = flavor;
		}
		if (aftertaste != null) {
			this.aftertaste = aftertaste;
		}
		if (totalScore != null) {
			this.totalScore = totalScore;
		}
		if (roastLevel != null) {
			this.roastLevel = roastLevel;
		}
	}
}
