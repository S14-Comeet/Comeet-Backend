package com.backend.domain.store.service.command;

import java.math.BigDecimal;

import com.backend.domain.store.entity.Store;

public interface StoreCommandService {

	Store createStore(Store store);

	Store updateStore(Store store);

	void deleteStore(Long storeId);

	void updateRatingStats(Long storeId, BigDecimal averageRating, Integer reviewCount);

	void incrementVisitCount(Long storeId);
}
