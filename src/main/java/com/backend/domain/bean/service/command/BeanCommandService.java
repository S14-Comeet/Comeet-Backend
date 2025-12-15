package com.backend.domain.bean.service.command;

import com.backend.domain.bean.entity.Bean;

public interface BeanCommandService {
	int insert(Bean bean);

	int update(Bean bean);

	int softDelete(Long beanId);
}
