package com.backend.domain.bean.mapper.query;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.backend.domain.bean.dto.common.BeanFlavorDto;
import com.backend.domain.flavor.entity.Flavor;

@Mapper
public interface BeanFlavorQueryMapper {
	List<Flavor> findFlavorsByBeanId(@Param("beanId") Long beanId);

	List<BeanFlavorDto> findFlavorsByBeanIds(@Param("beanIds") List<Long> beanIds);
}
