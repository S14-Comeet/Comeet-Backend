package com.backend.domain.store.service.command;

import com.backend.domain.store.entity.Store;

public interface StoreCommandService {

	Store createStore(Store store);

	Store updateStore(Store store);

	void deleteStore(Long storeId);
}
