package com.backend.domain.store.mapper.query;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.backend.domain.store.entity.Store;
import com.backend.domain.store.vo.StoreSearchBoundsVo;

@Mapper
public interface StoreQueryMapper {

	List<Store> findStoresWithinBounds(@Param("boundsVo") StoreSearchBoundsVo boundsVo);

	Optional<Store> findById(@Param("storeId") Long storeId);
}
