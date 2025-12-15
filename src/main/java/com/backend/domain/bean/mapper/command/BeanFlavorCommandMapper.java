package com.backend.domain.bean.mapper.command;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BeanFlavorCommandMapper {
	int insertBeanFlavors(@Param("beanId") Long beanId, @Param("flavorIds") List<Long> flavorIds);

	int deleteBeanFlavors(@Param("beanId") Long beanId);

}
