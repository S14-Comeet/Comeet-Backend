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
public class Review {
	private Long id;
	private Long visitId;
	private Long userId;
	private Long storeId;
	private Long menuId;
	private String textContent;
	private Boolean isPublic;
	private String imageUrl;
	private LocalDateTime deletedAt;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public void softDelete() {
		this.deletedAt = LocalDateTime.now();
	}

	public boolean isDeleted() {
		return this.deletedAt != null;
	}
}
