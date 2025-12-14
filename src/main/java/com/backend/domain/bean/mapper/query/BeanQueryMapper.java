package com.backend.domain.bean.mapper.query;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.backend.domain.bean.entity.Bean;

@Mapper
public interface BeanQueryMapper {
	Optional<Bean> findById(Long beanId);

	List<Bean> findAllByRoasteryId(Long roasteryId);

	List<Bean> findAllByIds(List<Long> beanIds);
}
