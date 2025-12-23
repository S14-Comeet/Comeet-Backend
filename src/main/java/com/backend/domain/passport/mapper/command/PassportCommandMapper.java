package com.backend.domain.passport.mapper.command;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.backend.domain.passport.entity.Passport;

@Mapper
public interface PassportCommandMapper {

    void insertPassport(Passport passport);

    void insertPassportVisit(@Param("passportId") Long passportId, @Param("visitId") Long visitId);

    void updateCoverImage(@Param("passportId") Long passportId, @Param("imageUrl") String imageUrl);
}
