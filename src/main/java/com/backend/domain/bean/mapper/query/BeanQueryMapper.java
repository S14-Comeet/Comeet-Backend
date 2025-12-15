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

	List<Bean> findByRoasteryId(Long roasteryId, Pageable pageable);

	int countByRoasteryId(Long roasteryId);

	List<Bean> findByCountryContaining(String keyword, Pageable pageable);

	int countByCountryContaining(String keyword);
}
