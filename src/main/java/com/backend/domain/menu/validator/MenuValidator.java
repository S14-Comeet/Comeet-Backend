package com.backend.domain.menu.validator;

import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.MenuException;
import com.backend.domain.menu.entity.Menu;
import com.backend.domain.store.entity.Store;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MenuValidator {

	public void validateStoreOwnership(final Store store, final Long userId) {
		if (!store.getOwnerId().equals(userId)) {
			throw new MenuException(ErrorCode.MENU_ACCESS_DENIED);
		}
	}

	public void validateNotDeleted(final Menu menu) {
		if (menu.getDeletedAt() != null) {
			throw new MenuException(ErrorCode.MENU_ALREADY_DELETED);
		}
	}
}
