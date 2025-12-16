package com.backend.domain.store.service.query;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.StoreException;
import com.backend.domain.store.entity.Store;
import com.backend.domain.store.mapper.query.StoreQueryMapper;
import com.backend.domain.store.vo.StoreSearchBoundsVo;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreQueryServiceImpl implements StoreQueryService {

	private final StoreQueryMapper queryMapper;

	@Override
	public Store findById(final Long storeId) {
		log.debug("[Store] 가맹점 조회 - id: {}", storeId);
		return queryMapper.findById(storeId)
			.orElseThrow(() -> new StoreException(ErrorCode.STORE_NOT_FOUND));
	}

	@Override
	public List<Store> findStoresWithinBounds(final StoreSearchBoundsVo boundsVo) {
		log.debug("[Store] Bounding Box 내 가맹점 조회 - lat: [{}, {}], lng: [{}, {}], categories: {}, keyword: {}",
			boundsVo.minLatitude(), boundsVo.maxLatitude(), boundsVo.minLongitude(), boundsVo.maxLongitude(), 
			boundsVo.categories(), boundsVo.keyword());
		List<Store> stores = queryMapper.findStoresWithinBounds(boundsVo);
		log.debug("[Store] 조회된 가맹점 수: {}", stores.size());
		return stores;
	}
}
