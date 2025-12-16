package com.backend.domain.menu.validator;

import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.MenuException;
import com.backend.domain.menu.entity.Menu;
import com.backend.domain.store.entity.Store;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MenuValidator {

	public static void validateStoreOwnership(Store store, Long userId) {
		if (!store.getOwnerId().equals(userId)) {
			throw new MenuException(ErrorCode.MENU_ACCESS_DENIED);
		}
	}

	public static void validateMenuBelongsToStore(Menu menu, Long storeId) {
		if (!menu.getStoreId().equals(storeId)) {
			throw new MenuException(ErrorCode.MENU_ACCESS_DENIED);
		}
	}

	public static void validateNotDeleted(Menu menu) {
		if (menu.getDeletedAt() != null) {
			throw new MenuException(ErrorCode.MENU_NOT_FOUND);
		}
	}
}
