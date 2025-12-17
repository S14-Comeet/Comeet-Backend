package com.backend.domain.menu.mapper.command;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.backend.domain.menu.entity.Menu;

@Mapper
public interface MenuCommandMapper {
	int insert(@Param("menu") Menu menu);

	int update(@Param("menu") Menu menu);

	int softDelete(@Param("menuId") Long menuId);

	int insertMenuBeanMapping(
		@Param("menuId") Long menuId,
		@Param("beanId") Long beanId,
		@Param("isBlended") Boolean isBlended
	);

	int deleteMenuBeanMapping(
		@Param("menuId") Long menuId,
		@Param("beanId") Long beanId
	);
}
