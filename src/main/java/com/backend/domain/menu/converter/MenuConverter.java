package com.backend.domain.menu.converter;

import java.util.List;

import com.backend.domain.bean.converter.BeanConverter;
import com.backend.domain.bean.entity.Bean;
import com.backend.domain.menu.dto.request.MenuBeanMappingReqDto;
import com.backend.domain.menu.dto.response.MenuBeanMappingResDto;
import com.backend.domain.menu.dto.response.MenuDetailResDto;
import com.backend.domain.menu.dto.response.MenuResDto;
import com.backend.domain.menu.entity.Menu;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MenuConverter {

	public static MenuResDto toMenuResDto(Menu menu) {
		return MenuResDto.builder()
			.id(menu.getId())
			.storeId(menu.getStoreId())
			.name(menu.getName())
			.description(menu.getDescription())
			.price(menu.getPrice())
			.category(menu.getCategory())
			.imageUrl(menu.getImageUrl())
			.build();
	}

	public static MenuDetailResDto toMenuDetailResDto(Menu menu, List<Bean> beans) {
		return MenuDetailResDto.builder()
			.id(menu.getId())
			.storeId(menu.getStoreId())
			.name(menu.getName())
			.description(menu.getDescription())
			.price(menu.getPrice())
			.category(menu.getCategory())
			.imageUrl(menu.getImageUrl())
			.beanBadges(BeanConverter.toBeanBadgeDtos(beans))
			.build();
	}

	public MenuBeanMappingResDto toMenuBeanMappingResDto(
		final Long menuId,
		final MenuBeanMappingReqDto reqDto,
		final boolean isConnected
	) {
		return MenuBeanMappingResDto.builder()
			.menuId(menuId)
			.beanId(reqDto.beanId())
			.isBlended(reqDto.isBlended())
			.isConnected(isConnected)
			.build();
	}

}
