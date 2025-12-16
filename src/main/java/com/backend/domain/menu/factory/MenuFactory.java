package com.backend.domain.menu.factory;

import org.springframework.stereotype.Component;

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
			.name(reqDto.name() != null ? reqDto.name() : existingMenu.getName())
			.description(reqDto.description() != null ? reqDto.description() : existingMenu.getDescription())
			.price(reqDto.price() != null ? reqDto.price() : existingMenu.getPrice())
			.category(reqDto.category() != null ? reqDto.category() : existingMenu.getCategory())
			.imageUrl(reqDto.imageUrl() != null ? reqDto.imageUrl() : existingMenu.getImageUrl())
			.deletedAt(existingMenu.getDeletedAt())
			.build();
	}
}
