package com.backend.domain.bean.mapper.command;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.backend.domain.bean.entity.Bean;

@Mapper
public interface BeanCommandMapper {
	int insert(@Param("bean") Bean bean);

	int update(@Param("bean") Bean bean);

	int softDelete(@Param("id") Long id);
}
