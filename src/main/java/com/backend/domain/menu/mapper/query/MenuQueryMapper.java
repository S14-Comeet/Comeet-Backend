package com.backend.domain.menu.mapper.query;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;

import com.backend.domain.menu.entity.Menu;

@Mapper
public interface MenuQueryMapper {
	Optional<Menu> findById(Long menuId);

	List<Menu> findAllByStoreId(Long storeId, Pageable pageable);

	int countAllByStoreId(Long storeId);

	List<Menu> findAllByCategory(String category, Pageable pageable);

	int countAllByCategory(String category);
}
