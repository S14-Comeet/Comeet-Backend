package com.backend.domain.bean.service.command;

import java.util.List;

public interface BeanFlavorCommandService {
	int insertBeanFlavors(Long beanId, List<Long> flavorIds);

	int deleteBeanFlavors(Long beanId);
}
