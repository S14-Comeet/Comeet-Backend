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
		return commandMapper.insertBeanFlavors(beanId,flavorIds);
	}

	@Override
	public int deleteBeanFlavors(final Long beanId) {
		return commandMapper.deleteBeanFlavors(beanId);
	}
}
