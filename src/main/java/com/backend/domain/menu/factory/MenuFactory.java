package com.backend.domain.menu.factory;

import org.springframework.stereotype.Component;

import com.backend.common.util.ObjectUtils;
import com.backend.domain.menu.dto.request.MenuCreateReqDto;
import com.backend.domain.menu.dto.request.MenuUpdateReqDto;
import com.backend.domain.menu.entity.Menu;
import com.backend.domain.menu.validator.MenuValidator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuFactory {

	private final MenuValidator menuValidator;

	public Menu create(Long storeId, MenuCreateReqDto reqDto) {
		Menu menu = Menu.builder()
			.storeId(storeId)
			.name(reqDto.name())
			.description(reqDto.description())
			.price(reqDto.price())
			.category(reqDto.category())
			.imageUrl(reqDto.imageUrl())
			.build();

		menuValidator.validate(menu);
		return menu;
	}

	public Menu createForUpdate(Menu existingMenu, MenuUpdateReqDto reqDto) {
		Menu menu = Menu.builder()
			.id(existingMenu.getId())
			.storeId(existingMenu.getStoreId())
			.name(ObjectUtils.getOrDefault(reqDto.name(), existingMenu.getName()))
			.description(ObjectUtils.getOrDefault(reqDto.description(), existingMenu.getDescription()))
			.price(ObjectUtils.getOrDefault(reqDto.price(), existingMenu.getPrice()))
			.category(ObjectUtils.getOrDefault(reqDto.category(), existingMenu.getCategory()))
			.imageUrl(ObjectUtils.getOrDefault(reqDto.imageUrl(), existingMenu.getImageUrl()))
			.createdAt(existingMenu.getCreatedAt())
			.deletedAt(existingMenu.getDeletedAt())
			.build();

		menuValidator.validate(menu);
		return menu;
	}

}
