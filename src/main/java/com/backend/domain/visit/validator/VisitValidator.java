package com.backend.domain.visit.validator;

import static com.backend.common.error.ErrorCode.*;

import org.springframework.stereotype.Component;

import com.backend.common.error.exception.VisitException;
import com.backend.common.validator.Validator;
import com.backend.domain.visit.entity.Visit;

@Component
public class VisitValidator implements Validator<Visit> {

	private static final double KOREA_MIN_LATITUDE = 33.0;
	private static final double KOREA_MAX_LATITUDE = 43.0;
	private static final double KOREA_MIN_LONGITUDE = 124.0;
	private static final double KOREA_MAX_LONGITUDE = 132.0;

	@Override
	public void validate(final Visit visit) {
		validateNotNull(visit);
		validateRequiredFields(visit);
		validateKoreaBoundary(visit);
	}

	private void validateNotNull(final Visit visit) {
		if (visit == null) {
			throw new VisitException(INVALID_VISIT_REQUEST);
		}
	}

	private void validateRequiredFields(final Visit visit) {
		if (visit.getMenuId() == null) {
			throw new VisitException(MENU_ID_REQUIRED);
		}
		if (visit.getLatitude() == null || visit.getLongitude() == null) {
			throw new VisitException(COORDINATES_REQUIRED);
		}
	}

	private void validateKoreaBoundary(final Visit visit) {
		Double latitude = visit.getLatitude();
		Double longitude = visit.getLongitude();

		if (isOutOfKorea(latitude, longitude)) {
			throw new VisitException(LOCATION_OUT_OF_KOREA);
		}
	}

	private boolean isOutOfKorea(final Double latitude, final Double longitude) {
		return latitude < KOREA_MIN_LATITUDE || latitude > KOREA_MAX_LATITUDE
			|| longitude < KOREA_MIN_LONGITUDE || longitude > KOREA_MAX_LONGITUDE;
	}
}

