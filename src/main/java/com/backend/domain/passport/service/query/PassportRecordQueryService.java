package com.backend.domain.passport.service.query;

import java.util.List;

import com.backend.domain.passport.dto.common.PassportRecordDto;

public interface PassportRecordQueryService {

	List<PassportRecordDto> findRecordsByPassportId(Long passportId);
}
