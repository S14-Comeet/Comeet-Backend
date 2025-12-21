package com.backend.domain.passport.dto.response;

import java.util.List;

import com.backend.domain.passport.dto.common.PassportInfoDto;
import com.backend.domain.passport.dto.common.PassportRecordDto;

import lombok.Builder;

@Builder
public record PassportDetailResDto(
	Long passportId,
	Integer year,
	Integer month,
	String coverImageUrl,
	PassportInfoDto info,
	List<PassportRecordDto> records
) {
}
