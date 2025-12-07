package com.backend.domain.visit.converter;

import com.backend.domain.visit.dto.response.VerifiedResDto;
import com.backend.domain.visit.entity.Visit;

import lombok.experimental.UtilityClass;

@UtilityClass
public class VisitConverter {

	public VerifiedResDto toVerifiedResDto(final Visit visit) {
		return VerifiedResDto.builder()
			.visitId(visit.getId())
			.isVerified(visit.getIsVerified())
			.build();
	}
}