package com.backend.domain.review.dto.request;

import java.util.List;

public record ReviewUpdateReqDto(
	String content,
	Boolean isPublic,
	String imageUrl,
	List<Long> flavorWheelIdList
) {
}