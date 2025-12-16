package com.backend.domain.menu.service.command;

import com.backend.domain.menu.entity.Menu;

public interface MenuCommandService {
	int insert(Menu menu);

	int update(Menu menu);

	int softDelete(Long menuId);

	int insertMenuBeanMapping(Long menuId, Long beanId, Boolean isBlended);

	int deleteMenuBeanMapping(Long menuId, Long beanId);

	int countMenuBeanMapping(Long menuId, Long beanId);
}
