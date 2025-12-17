package com.backend.domain.store.service.query;

import java.util.List;

import com.backend.domain.store.entity.Store;
import com.backend.domain.store.vo.StoreSearchBoundsVo;

public interface StoreQueryService {

	Store findById(Long storeId);

	List<Store> findStoresWithinBounds(StoreSearchBoundsVo boundsVo);
}
