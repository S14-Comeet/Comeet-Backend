package com.backend.domain.visit.service.facade;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.VisitException;
import com.backend.common.util.GeoUtils;
import com.backend.common.util.PageUtils;
import com.backend.domain.user.entity.User;
import com.backend.domain.user.validator.UserValidator;
import com.backend.domain.visit.converter.VisitConverter;
import com.backend.domain.visit.dto.common.VisitInfoDto;
import com.backend.domain.visit.dto.common.VisitPageDto;
import com.backend.domain.visit.dto.request.VerifyReqDto;
import com.backend.domain.visit.dto.response.VerifiedResDto;
import com.backend.domain.visit.entity.Visit;
import com.backend.domain.visit.factory.VisitFactory;
import com.backend.domain.visit.service.command.VisitCommandService;
import com.backend.domain.visit.service.query.VisitQueryService;
import com.backend.domain.visit.validator.VisitValidator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class VisitFacadeService {
	private static final int ALLOWABLE_RANGE = 100;

	private final VisitCommandService visitCommandService;
	private final VisitQueryService visitQueryService;
	private final VisitValidator visitValidator;
	private final VisitFactory visitFactory;

	private final UserValidator userValidator;

	public VerifiedResDto verifyVisit(final User user, final VerifyReqDto reqDto) {
		userValidator.validate(user);
		Boolean isVerified = checkDistance(reqDto);
		Visit visit = visitFactory.create(user, reqDto, isVerified);

		if (visitCommandService.save(visit) == 0) {
			throw new VisitException(ErrorCode.VISIT_SAVE_FAILED);
		}

		return VisitConverter.toVerifiedResDto(visit);
	}

	private Boolean checkDistance(final VerifyReqDto reqDto) {
		double calculatedDistance = GeoUtils.calculateHaversineDistance(
			reqDto.storeLocationDto().latitude(),
			reqDto.storeLocationDto().longitude(),
			reqDto.userLocationDto().latitude(),
			reqDto.userLocationDto().longitude()
		);

		return GeoUtils.isWithinRadius(calculatedDistance, ALLOWABLE_RANGE);
	}

	public VisitInfoDto findVisitById(final User user, final Long visitId) {
		userValidator.validate(user);
		Visit visit = visitQueryService.findById(visitId);
		visitValidator.validateVisitBelongsToUser(visit.getUserId(), user.getId());
		return VisitConverter.toVisitInfoDto(visit);
	}

	public Page<VisitPageDto> findAllWithPageableUserId(final User user, final int page, final int size) {
		userValidator.validate(user);
		Pageable pageable = PageUtils.getPageable(page, size);
		List<VisitPageDto> list = visitQueryService.findAllByUserId(user.getId(), pageable).stream()
			.map(VisitConverter::toVisitPageDto).toList();
		int total = visitQueryService.countAllByUserId(user.getId());
		return new PageImpl<>(list, pageable, total);
	}
}
