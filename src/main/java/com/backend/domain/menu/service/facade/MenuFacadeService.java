package com.backend.domain.menu.service.facade;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.MenuException;
import com.backend.common.util.PageUtils;
import com.backend.domain.bean.entity.Bean;
import com.backend.domain.bean.service.query.BeanQueryService;
import com.backend.domain.menu.converter.MenuConverter;
import com.backend.domain.menu.dto.request.MenuBeanMappingReqDto;
import com.backend.domain.menu.dto.request.MenuCreateReqDto;
import com.backend.domain.menu.dto.request.MenuUpdateReqDto;
import com.backend.domain.menu.dto.response.MenuBeanMappingResDto;
import com.backend.domain.menu.dto.response.MenuDetailResDto;
import com.backend.domain.menu.dto.response.MenuResDto;
import com.backend.domain.menu.entity.Menu;
import com.backend.domain.menu.factory.MenuFactory;
import com.backend.domain.menu.service.command.MenuCommandService;
import com.backend.domain.menu.service.query.MenuQueryService;
import com.backend.domain.menu.validator.MenuValidator;
import com.backend.domain.store.entity.Store;
import com.backend.domain.store.service.query.StoreQueryService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuFacadeService {

	private final MenuCommandService menuCommandService;
	private final MenuQueryService menuQueryService;
	private final StoreQueryService storeQueryService;
	private final BeanQueryService beanQueryService;

	public MenuResDto createMenu(Long storeId, Long userId, MenuCreateReqDto reqDto) {
		Store store = storeQueryService.findById(storeId);
		MenuValidator.validateStoreOwnership(store, userId);

		Menu menu = MenuFactory.create(storeId, reqDto);

		int affectedRows = menuCommandService.insert(menu);
		if (affectedRows == 0) {
			throw new MenuException(ErrorCode.MENU_SAVE_FAILED);
		}

		return MenuConverter.toMenuResDto(menu);
	}

	public Page<MenuResDto> getMenusByStore(Long storeId, int page, int size) {
		storeQueryService.findById(storeId);

		return PageUtils.buildPageResponse(
			page, size,
			pageable -> menuQueryService.findByStoreId(storeId, pageable),
			() -> menuQueryService.countByStoreId(storeId),
			MenuConverter::toMenuResDto
		);
	}

	public MenuDetailResDto getMenuDetail(Long menuId) {
		Menu menu = menuQueryService.findById(menuId);
		MenuValidator.validateNotDeleted(menu);

		List<Bean> beans = beanQueryService.findByMenuId(menuId);

		return MenuConverter.toMenuDetailResDto(menu, beans);
	}

	public MenuResDto updateMenu(Long menuId, Long userId, MenuUpdateReqDto reqDto) {
		Menu existingMenu = menuQueryService.findById(menuId);
		MenuValidator.validateNotDeleted(existingMenu);

		Store store = storeQueryService.findById(existingMenu.getStoreId());
		MenuValidator.validateStoreOwnership(store, userId);

		Menu updatedMenu = MenuFactory.createForUpdate(existingMenu, reqDto);
		int affectedRows = menuCommandService.update(updatedMenu);
		if (affectedRows == 0) {
			throw new MenuException(ErrorCode.MENU_UPDATE_FAILED);
		}

		return MenuConverter.toMenuResDto(updatedMenu);
	}

	public void deleteMenu(Long menuId, Long userId) {
		Menu menu = menuQueryService.findById(menuId);
		MenuValidator.validateNotDeleted(menu);

		Store store = storeQueryService.findById(menu.getStoreId());
		MenuValidator.validateStoreOwnership(store, userId);

		int affectedRows = menuCommandService.softDelete(menuId);
		if (affectedRows == 0) {
			throw new MenuException(ErrorCode.MENU_DELETE_FAILED);
		}
	}

	public MenuBeanMappingResDto addBeanToMenu(Long menuId, Long userId, MenuBeanMappingReqDto reqDto) {
		Menu menu = menuQueryService.findById(menuId);
		MenuValidator.validateNotDeleted(menu);

		Store store = storeQueryService.findById(menu.getStoreId());
		MenuValidator.validateStoreOwnership(store, userId);

		int existingMappingCount = menuCommandService.countMenuBeanMapping(menuId, reqDto.beanId());
		if (existingMappingCount > 0) {
			throw new MenuException(ErrorCode.MENU_BEAN_ALREADY_MAPPED);
		}

		boolean isBlended = reqDto.isBlended() != null;
		int affectedRows = menuCommandService.insertMenuBeanMapping(menuId, reqDto.beanId(), isBlended);
		if (affectedRows == 0) {
			throw new MenuException(ErrorCode.MENU_BEAN_MAPPING_FAILED);
		}

		MenuBeanMappingReqDto normalizedReqDto = new MenuBeanMappingReqDto(reqDto.beanId(), isBlended);
		return MenuConverter.toMenuBeanMappingResDto(menuId, normalizedReqDto, affectedRows == 1);
	}

	public void removeBeanFromMenu(Long menuId, Long beanId, Long userId) {
		Menu menu = menuQueryService.findById(menuId);
		MenuValidator.validateNotDeleted(menu);

		Store store = storeQueryService.findById(menu.getStoreId());
		MenuValidator.validateStoreOwnership(store, userId);

		int affectedRows = menuCommandService.deleteMenuBeanMapping(menuId, beanId);
		if (affectedRows == 0) {
			throw new MenuException(ErrorCode.MENU_BEAN_UNMAPPING_FAILED);
		}
	}
}
