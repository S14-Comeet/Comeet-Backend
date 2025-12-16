package com.backend.domain.menu.service.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.domain.menu.entity.Menu;
import com.backend.domain.menu.mapper.command.MenuCommandMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuCommandServiceImpl implements MenuCommandService {

	private final MenuCommandMapper menuCommandMapper;

	@Override
	public int insert(Menu menu) {
		log.info("[Menu] 메뉴 저장 - storeId: {}, name: {}", menu.getStoreId(), menu.getName());
		return menuCommandMapper.insert(menu);
	}

	@Override
	public int update(Menu menu) {
		log.info("[Menu] 메뉴 업데이트 - id: {}, name: {}", menu.getId(), menu.getName());
		return menuCommandMapper.update(menu);
	}

	@Override
	public int softDelete(Long menuId) {
		log.info("[Menu] 메뉴 소프트 삭제 - id: {}", menuId);
		return menuCommandMapper.softDelete(menuId);
	}

	@Override
	public int insertMenuBeanMapping(Long menuId, Long beanId, Boolean isBlended) {
		log.info("[Menu] 메뉴-원두 매핑 생성 - menuId: {}, beanId: {}", menuId, beanId);
		return menuCommandMapper.insertMenuBeanMapping(menuId, beanId, isBlended);
	}

	@Override
	public int deleteMenuBeanMapping(Long menuId, Long beanId) {
		log.info("[Menu] 메뉴-원두 매핑 삭제 - menuId: {}, beanId: {}", menuId, beanId);
		return menuCommandMapper.deleteMenuBeanMapping(menuId, beanId);
	}

	@Override
	public int countMenuBeanMapping(Long menuId, Long beanId) {
		return menuCommandMapper.countMenuBeanMapping(menuId, beanId);
	}
}
