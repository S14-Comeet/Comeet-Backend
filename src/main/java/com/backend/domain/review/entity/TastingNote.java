package com.backend.domain.review.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TastingNote {
	private Long id;
	private Long reviewId;
	private Long flavorWheelId;

	public static TastingNote of(final Long reviewId, final Long flavorWheelId) {
		return TastingNote.builder()
			.reviewId(reviewId)
			.flavorWheelId(flavorWheelId)
			.build();
	}
}

