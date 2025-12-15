package com.backend.domain.bean.service.query;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.backend.domain.bean.entity.Bean;

public interface BeanQueryService {
	Bean findById(Long beanId);

	List<Bean> findAll(Pageable pageable);

	int countAll();

	List<Bean> findByRoasteryId(Long roasteryId, Pageable pageable);

	int countByRoasteryId(Long roasteryId);

	List<Bean> findByCountryContaining(String keyword, Pageable pageable);

	int countByCountryContaining(String keyword);
}
