package com.backend.domain.menu.service.query;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.backend.domain.menu.entity.Menu;

public interface MenuQueryService {
	Menu findById(Long menuId);

	List<Menu> findByStoreId(Long storeId, Pageable pageable);

	int countByStoreId(Long storeId);
}
