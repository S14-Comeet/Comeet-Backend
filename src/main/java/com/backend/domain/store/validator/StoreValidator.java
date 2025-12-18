package com.backend.domain.store.validator;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.StoreException;
import com.backend.common.validator.Validator;
import com.backend.domain.store.entity.Store;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StoreValidator implements Validator<Store> {

	@Override
	public void validate(final Store store) {
		validateNotNull(store);
		validateName(store.getName());
		validateAddress(store.getAddress());
		validateLocation(store.getLatitude(), store.getLongitude());
	}

	public void validateExitingStore(final Store store, final Long userId) {
		validateNotDeleted(store);
		validateOwnership(store, userId);
	}

	private void validateNotNull(final Store store) {
		if (store == null) {
			throw new StoreException(ErrorCode.BAD_REQUEST);
		}
	}

	private void validateName(final String name) {
		if (name == null || name.isBlank()) {
			throw new StoreException(ErrorCode.BAD_REQUEST);
		}
		if (name.length() > 100) {
			throw new StoreException(ErrorCode.BAD_REQUEST);
		}
	}

	private void validateAddress(final String address) {
		if (address == null || address.isBlank()) {
			throw new StoreException(ErrorCode.BAD_REQUEST);
		}
	}

	public void validateLocation(final BigDecimal latitude, final BigDecimal longitude) {
		if (latitude == null || longitude == null) {
			throw new StoreException(ErrorCode.INVALID_LOCATION);
		}

		if (latitude.compareTo(BigDecimal.valueOf(-90.0)) < 0 ||
			latitude.compareTo(BigDecimal.valueOf(90.0)) > 0) {
			throw new StoreException(ErrorCode.INVALID_LOCATION);
		}

		if (longitude.compareTo(BigDecimal.valueOf(-180.0)) < 0 ||
			longitude.compareTo(BigDecimal.valueOf(180.0)) > 0) {
			throw new StoreException(ErrorCode.INVALID_LOCATION);
		}
	}

	public void validateOwnership(final Store store, final Long userId) {
		if (store == null) {
			throw new StoreException(ErrorCode.STORE_NOT_FOUND);
		}

		if (!store.getOwnerId().equals(userId)) {
			throw new StoreException(ErrorCode.STORE_OWNER_ONLY);
		}
	}

	public void validateAccess(final Store store, final Long userId) {
		if (store == null) {
			throw new StoreException(ErrorCode.STORE_NOT_FOUND);
		}

		if (!store.getOwnerId().equals(userId)) {
			throw new StoreException(ErrorCode.STORE_ACCESS_DENIED);
		}
	}

	public void validateNotDeleted(final Store store) {
		if (store == null) {
			throw new StoreException(ErrorCode.STORE_NOT_FOUND);
		}

		if (store.getDeletedAt() != null) {
			throw new StoreException(ErrorCode.STORE_NOT_FOUND);
		}
	}
}
