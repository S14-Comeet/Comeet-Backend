package com.backend.domain.menu.factory;

import java.time.LocalDateTime;

import com.backend.domain.menu.dto.request.MenuCreateReqDto;
import com.backend.domain.menu.dto.request.MenuUpdateReqDto;
import com.backend.domain.menu.entity.Menu;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MenuFactory {

	public static Menu create(Long storeId, MenuCreateReqDto reqDto) {
		return Menu.builder()
			.storeId(storeId)
			.name(reqDto.name())
			.description(reqDto.description())
			.price(reqDto.price())
			.category(reqDto.category())
			.imageUrl(reqDto.imageUrl())
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();
	}

	public static Menu createForUpdate(Menu existingMenu, MenuUpdateReqDto reqDto) {
		return Menu.builder()
			.id(existingMenu.getId())
			.storeId(existingMenu.getStoreId())
			.name(reqDto.name() != null ? reqDto.name() : existingMenu.getName())
			.description(reqDto.description() != null ? reqDto.description() : existingMenu.getDescription())
			.price(reqDto.price() != null ? reqDto.price() : existingMenu.getPrice())
			.category(reqDto.category() != null ? reqDto.category() : existingMenu.getCategory())
			.imageUrl(reqDto.imageUrl() != null ? reqDto.imageUrl() : existingMenu.getImageUrl())
			.deletedAt(existingMenu.getDeletedAt())
			.createdAt(existingMenu.getCreatedAt())
			.updatedAt(LocalDateTime.now())
			.build();
	}
}
