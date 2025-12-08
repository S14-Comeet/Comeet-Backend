package com.backend.domain.review.entity;

import java.time.LocalDateTime;

import com.backend.domain.review.dto.request.ReviewUpdateReqDto;

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
	private Long userId;
	private Long menuId;
	private Long visitId;
	private String content;
	private String imageUrl;
	private Boolean isPublic;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;

	public void softDelete() {
		this.deletedAt = LocalDateTime.now();
	}

	public boolean isDeleted() {
		return this.deletedAt != null;
	}

	public void update(final ReviewUpdateReqDto reqDto) {
		if (reqDto.content() != null) {
			this.content = reqDto.content();
		}
		if (reqDto.isPublic() != null) {
			this.isPublic = reqDto.isPublic();
		}
		if (reqDto.imageUrl() != null) {
			this.imageUrl = reqDto.imageUrl();
		}
	}
}
