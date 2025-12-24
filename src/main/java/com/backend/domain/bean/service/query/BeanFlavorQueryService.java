package com.backend.domain.bean.service.query;

import java.util.List;

import com.backend.domain.bean.dto.common.BeanFlavorDto;
import com.backend.domain.bean.dto.response.BeanFlavorResDto;
import com.backend.domain.flavor.entity.Flavor;

public interface BeanFlavorQueryService {

	List<BeanFlavorDto> findFlavorIdsByBeanIds(List<Long> beanIds);

	List<Flavor> findFlavorsByBeanId(Long beanId);

	BeanFlavorResDto getBeanFlavors(Long beanId);
}
