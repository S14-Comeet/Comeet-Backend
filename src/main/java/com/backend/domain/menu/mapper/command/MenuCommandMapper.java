package com.backend.domain.menu.mapper.command;

import org.apache.ibatis.annotations.Mapper;

import com.backend.domain.menu.entity.Menu;

@Mapper
public interface MenuCommandMapper {
	int insert(Menu menu);

	int update(Menu menu);

	int softDelete(Long id);
}
