package com.backend.domain.store.service.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.StoreException;
import com.backend.domain.store.entity.Store;
import com.backend.domain.store.mapper.command.StoreCommandMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreCommandServiceImpl implements StoreCommandService {

	private final StoreCommandMapper storeCommandMapper;

	@Override
	public Store createStore(final Store store) {
		storeCommandMapper.save(store);
		log.info("[Store] 가맹점 생성 완료 - ID: {}", store.getId());
		return store;
	}

	@Override
	public Store updateStore(final Store store) {
		int affectedRows = storeCommandMapper.update(store);

		if (affectedRows == 0) {
			log.error("[Store] 가맹점 수정 실패 - ID: {}", store.getId());
			throw new StoreException(ErrorCode.STORE_NOT_FOUND);
		}

		log.info("[Store] 가맹점 수정 완료 - ID: {}", store.getId());
		return store;
	}

	@Override
	public void deleteStore(final Long storeId) {
		int affectedRows = storeCommandMapper.softDelete(storeId);

		if (affectedRows == 0) {
			log.error("[Store] 가맹점 삭제 실패 - ID: {}", storeId);
			throw new StoreException(ErrorCode.STORE_NOT_FOUND);
		}

		log.info("[Store] 가맹점 삭제 완료 - ID: {}", storeId);
	}
}
