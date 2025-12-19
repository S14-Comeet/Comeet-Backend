package com.backend.domain.passport.mapper.query;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.backend.domain.passport.entity.Passport;

@Mapper
public interface PassportQueryMapper {

	Passport findById(@Param("passportId") Long passportId);

	List<Passport> findAllByUserIdAndYear(@Param("userId") Long userId, @Param("year") Integer year);
}
