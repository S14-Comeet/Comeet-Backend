package com.backend.domain.bean.service.query.implement;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.backend.domain.bean.dto.common.BeanFlavorDto;
import com.backend.domain.bean.mapper.query.BeanFlavorQueryMapper;
import com.backend.domain.bean.service.query.BeanFlavorQueryService;
import com.backend.domain.flavor.entity.Flavor;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class BeanFlavorQueryServiceImpl implements BeanFlavorQueryService {
	private final BeanFlavorQueryMapper beanFlavorQueryMapper;

	@Override
	public List<BeanFlavorDto> findFlavorIdsByBeanIds(final List<Long> beanIds) {
		if (CollectionUtils.isEmpty(beanIds)) {
			return List.of();
		}

		List<BeanFlavorDto> flavorsByBeanIds = beanFlavorQueryMapper.findFlavorsByBeanIds(beanIds);
		log.info("[BeanFlavor] 원두 목록별 플레이버 조회 - beanIds 개수={}", flavorsByBeanIds.size());
		return flavorsByBeanIds;
	}

	@Override
	public List<Flavor> findFlavorsByBeanId(final Long beanId) {
		List<Flavor> flavors = beanFlavorQueryMapper.findFlavorsByBeanId(beanId);
		log.info("[BeanFlavor] 원두별 플레이버 조회 - beanId={}", beanId);
		return flavors;
	}
}
