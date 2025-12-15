package com.backend.domain.bean.mapper.query;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import com.backend.domain.bean.entity.Bean;

@Mapper
public interface BeanQueryMapper {
	Optional<Bean> findById(Long beanId);

	List<Bean> findAllByRoasteryId(Long roasteryId);

	List<Bean> findAllByIds(List<Long> beanIds);

	List<Bean> findAll(@Param("pageable") Pageable pageable);

	int countAll();

	List<Bean> findByRoasteryId(@Param("roasteryId") Long roasteryId, @Param("pageable") Pageable pageable);

	int countByRoasteryId(@Param("roasteryId") Long roasteryId);

	List<Bean> findByCountryContaining(@Param("keyword") String keyword, @Param("pageable") Pageable pageable);

	int countByCountryContaining(@Param("keyword") String keyword);
}
