package com.backend.domain.menu.service.command;

import com.backend.domain.menu.entity.Menu;

public interface MenuCommandService {
	void insert(Menu menu);

	void update(Menu menu);

	void softDelete(Long menuId);

	void insertMenuBeanMapping(Long menuId, Long beanId, Boolean isBlended);

	void deleteMenuBeanMapping(Long menuId, Long beanId);

	int countMenuBeanMapping(Long menuId, Long beanId);
}
