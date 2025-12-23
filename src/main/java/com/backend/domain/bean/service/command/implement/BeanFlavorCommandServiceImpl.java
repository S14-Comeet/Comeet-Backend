package com.backend.domain.bean.service.command.implement;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.domain.bean.mapper.command.BeanFlavorCommandMapper;
import com.backend.domain.bean.service.command.BeanFlavorCommandService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class BeanFlavorCommandServiceImpl implements BeanFlavorCommandService {
	private final BeanFlavorCommandMapper commandMapper;

	@Override
	public int insertBeanFlavors(final Long beanId, final List<Long> flavorIds) {
		log.info("[BeanFlavor] 원두-플레이버 매핑 생성 - beanId={}, flavorIds={}", beanId, flavorIds);
		return commandMapper.insertBeanFlavors(beanId, flavorIds);
	}

	@Override
	public int deleteBeanFlavors(final Long beanId) {
		log.info("[BeanFlavor] 원두-플레이버 매핑 삭제 - beanId={}", beanId);
		return commandMapper.deleteBeanFlavors(beanId);
	}
}
