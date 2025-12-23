package com.backend.domain.beanscore.service.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.domain.beanscore.entity.BeanScore;
import com.backend.domain.beanscore.mapper.command.BeanScoreCommandMapper;

import lombok.RequiredArgsConstructor;

/**
 * BeanScore Command Service 구현체
 */
@Service
@Transactional
@RequiredArgsConstructor
public class BeanScoreCommandServiceImpl implements BeanScoreCommandService {

	private final BeanScoreCommandMapper beanScoreCommandMapper;

	@Override
	public void save(BeanScore beanScore) {
		beanScoreCommandMapper.insert(beanScore);
	}

	@Override
	public void update(BeanScore beanScore) {
		beanScoreCommandMapper.update(beanScore);
	}

	@Override
	public void deleteByBeanId(Long beanId) {
		beanScoreCommandMapper.deleteByBeanId(beanId);
	}
}
