package com.backend.domain.bean.mapper.command;

import org.apache.ibatis.annotations.Mapper;

import com.backend.domain.bean.entity.Bean;

@Mapper
public interface BeanCommandMapper {
	int insert(Bean bean);

	int update(Bean bean);

	int softDelete(Long id);
}
