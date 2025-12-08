package com.backend.domain.review.dto.request;

import java.util.List;

public record ReviewReqDto(
	Long visitId,
	Long menuId,
	String content,
	Boolean isPublic,
	String imageUrl,
	List<Long> flavorWheelIdList
) {
}
