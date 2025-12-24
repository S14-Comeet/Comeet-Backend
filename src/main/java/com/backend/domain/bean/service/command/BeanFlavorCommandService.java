package com.backend.domain.bean.service.command;

import java.util.List;

import com.backend.domain.bean.dto.request.BeanFlavorCreateReqDto;
import com.backend.domain.bean.dto.response.BeanFlavorResDto;

public interface BeanFlavorCommandService {
	int insertBeanFlavors(Long beanId, List<Long> flavorIds);

	int deleteBeanFlavors(Long beanId);

	BeanFlavorResDto addBeanFlavors(Long beanId, BeanFlavorCreateReqDto reqDto);

	BeanFlavorResDto updateBeanFlavors(Long beanId, BeanFlavorCreateReqDto reqDto);

	void deleteBeanFlavorsByBeanId(Long beanId);
}
