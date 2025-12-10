package com.backend.domain.review.entity;

import java.time.LocalDateTime;

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
	private Long flavorId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}

