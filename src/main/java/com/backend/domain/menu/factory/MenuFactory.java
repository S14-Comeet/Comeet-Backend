package com.backend.domain.menu.factory;

import org.springframework.stereotype.Component;

import com.backend.common.util.ObjectUtils;
import com.backend.domain.menu.dto.request.MenuCreateReqDto;
import com.backend.domain.menu.dto.request.MenuUpdateReqDto;
import com.backend.domain.menu.entity.Menu;

@Component
public class MenuFactory {

	public Menu create(Long storeId, MenuCreateReqDto reqDto) {
		return Menu.builder()
			.storeId(storeId)
			.name(reqDto.name())
			.description(reqDto.description())
			.price(reqDto.price())
			.category(reqDto.category())
			.imageUrl(reqDto.imageUrl())
			.build();
	}

	public Menu createForUpdate(Menu existingMenu, MenuUpdateReqDto reqDto) {
		return Menu.builder()
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
	}

}
