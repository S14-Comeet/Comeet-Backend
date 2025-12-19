package com.backend.domain.passport.mapper.query;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.backend.domain.passport.dto.common.PassportRecordDto;

@Mapper
public interface PassportRecordQueryMapper {

	List<PassportRecordDto> findRecordsByPassportId(@Param("passportId") Long passportId);
}
