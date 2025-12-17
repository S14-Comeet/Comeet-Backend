package com.backend.domain.menu.mapper.query;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import com.backend.domain.menu.entity.Menu;

@Mapper
public interface MenuQueryMapper {
	Optional<Menu> findById(@Param("menuId") Long menuId);

	List<Menu> findByStoreId(
		@Param("storeId") Long storeId,
		@Param("pageable") Pageable pageable
	);

	int countByStoreId(@Param("storeId") Long storeId);

	int countMenuBeanMapping(Long menuId, Long beanId);
}
