package com.backend.domain.passport.service.command;

import com.backend.domain.passport.entity.Passport;

public interface PassportCommandService {

    Long createPassport(Passport passport);

    void addPassportVisit(Long passportId, Long visitId);
}
