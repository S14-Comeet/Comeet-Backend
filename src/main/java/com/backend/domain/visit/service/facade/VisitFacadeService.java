package com.backend.domain.visit.service.facade;

import org.springframework.stereotype.Service;

import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.VisitException;
import com.backend.common.util.GeoUtils;
import com.backend.domain.user.entity.User;
import com.backend.domain.user.validator.UserValidator;
import com.backend.domain.visit.converter.VisitConverter;
import com.backend.domain.visit.dto.request.VerifyReqDto;
import com.backend.domain.visit.dto.response.VerifiedResDto;
import com.backend.domain.visit.entity.Visit;
import com.backend.domain.visit.factory.VisitFactory;
import com.backend.domain.visit.service.command.VisitCommandService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class VisitFacadeService {
	private static final int ALLOWABLE_RANGE = 100;

	private final VisitCommandService visitCommandService;
	private final UserValidator userValidator;
	private final VisitFactory visitFactory;

	public VerifiedResDto verifyVisit(final User user, final VerifyReqDto reqDto) {
		userValidator.validate(user);
		Boolean isVerified = checkDistance(reqDto);
		Visit visit = visitFactory.create(user, reqDto, isVerified);

		if (visitCommandService.save(visit) == 0) {
			throw new VisitException(ErrorCode.VISIT_SAVE_FAILED);
		}

		return VisitConverter.toVerifiedResDto(visit);
	}

	private static Boolean checkDistance(final VerifyReqDto reqDto) {
		double calculatedDistance = GeoUtils.calculateHaversineDistance(
			reqDto.storeLocationDto().latitude(),
			reqDto.storeLocationDto().longitude(),
			reqDto.userLocationDto().latitude(),
			reqDto.userLocationDto().longitude()
		);

		return GeoUtils.isWithinRadius(calculatedDistance, ALLOWABLE_RANGE);
	}
}
