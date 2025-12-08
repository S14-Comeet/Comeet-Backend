package com.backend.domain.review.dto.request;

public record ReviewReqDto(
	Long visitId,
	Long menuId,
	String textContent,
	Boolean isPublic,
	String imageUrl
) {
}
