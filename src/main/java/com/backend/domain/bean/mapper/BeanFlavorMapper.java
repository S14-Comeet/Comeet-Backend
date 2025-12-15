package com.backend.domain.bean.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.backend.domain.flavor.entity.Flavor;

@Mapper
public interface BeanFlavorMapper {
	int insertBeanFlavors(@Param("beanId") Long beanId, @Param("flavorIds") List<Long> flavorIds);

	int deleteBeanFlavors(@Param("beanId") Long beanId);

	List<Flavor> findFlavorsByBeanId(@Param("beanId") Long beanId);

	List<Flavor> findFlavorsByBeanIds(@Param("beanIds") List<Long> beanIds);
}
