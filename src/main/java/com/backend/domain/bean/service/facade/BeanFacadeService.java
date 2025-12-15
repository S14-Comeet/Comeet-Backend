package com.backend.domain.bean.service.facade;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.BeanException;
import com.backend.common.util.PageUtils;
import com.backend.domain.bean.converter.BeanConverter;
import com.backend.domain.bean.dto.request.BeanCreateReqDto;
import com.backend.domain.bean.dto.request.BeanUpdateReqDto;
import com.backend.domain.bean.dto.response.BeanResDto;
import com.backend.domain.bean.entity.Bean;
import com.backend.domain.bean.factory.BeanFactory;
import com.backend.domain.bean.mapper.BeanFlavorMapper;
import com.backend.domain.bean.service.command.BeanCommandService;
import com.backend.domain.bean.service.query.BeanQueryService;
import com.backend.domain.flavor.entity.Flavor;
import com.backend.domain.roastery.entity.Roastery;
import com.backend.domain.roastery.service.query.RoasteryQueryService;
import com.backend.domain.roastery.validator.RoasteryValidator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class BeanFacadeService {

	private final BeanQueryService beanQueryService;
	private final BeanCommandService beanCommandService;
	private final RoasteryQueryService roasteryQueryService;
	private final BeanFlavorMapper beanFlavorMapper;

	private final BeanFactory beanFactory;
	private final RoasteryValidator roasteryValidator;

	public BeanResDto createBean(final Long userId, final BeanCreateReqDto reqDto) {
		Roastery roastery = roasteryQueryService.findById(reqDto.roasteryId());
		roasteryValidator.validateOwnership(roastery, userId);

		Bean bean = beanFactory.create(reqDto);
		int affectedRows = beanCommandService.insert(bean);
		if (affectedRows == 0) {
			throw new BeanException(ErrorCode.BEAN_SAVE_FAILED);
		}

		if (!CollectionUtils.isEmpty(reqDto.flavorIds())) {
			beanFlavorMapper.insertBeanFlavors(bean.getId(), reqDto.flavorIds());
		}

		return getBeanResDtoWithFlavors(bean);
	}

	public BeanResDto updateBean(final Long beanId, final Long userId, final BeanUpdateReqDto reqDto) {
		Bean existingBean = beanQueryService.findById(beanId);
		Roastery roastery = roasteryQueryService.findById(existingBean.getRoasteryId());
		roasteryValidator.validateOwnership(roastery, userId);

		Bean updatedBean = beanFactory.createForUpdate(existingBean, reqDto);
		int affectedRows = beanCommandService.update(updatedBean);
		if (affectedRows == 0) {
			throw new BeanException(ErrorCode.BEAN_UPDATE_FAILED);
		}

		// Flavor 매핑 업데이트
		if (reqDto.flavorIds() != null) {
			beanFlavorMapper.deleteBeanFlavors(beanId);
			if (!reqDto.flavorIds().isEmpty()) {
				beanFlavorMapper.insertBeanFlavors(beanId, reqDto.flavorIds());
			}
		}

		return getBeanResDtoWithFlavors(updatedBean);
	}

	public void deleteBean(final Long beanId, final Long userId) {
		Bean existingBean = beanQueryService.findById(beanId);
		Roastery roastery = roasteryQueryService.findById(existingBean.getRoasteryId());
		roasteryValidator.validateOwnership(roastery, userId);

		int affectedRows = beanCommandService.softDelete(beanId);
		if (affectedRows == 0) {
			throw new BeanException(ErrorCode.BEAN_DELETE_FAILED);
		}
	}

	public BeanResDto getBean(final Long beanId) {
		Bean bean = beanQueryService.findById(beanId);
		return getBeanResDtoWithFlavors(bean);
	}

	public Page<BeanResDto> getAllBeans(final int page, final int size) {
		return PageUtils.buildPageResponse(
			page, size,
			beanQueryService::findAll,
			beanQueryService::countAll,
			this::getBeanResDtoWithFlavors
		);
	}

	public Page<BeanResDto> getBeansByRoastery(final Long roasteryId, final int page, final int size) {
		Roastery roastery = roasteryQueryService.findById(roasteryId);

		return PageUtils.buildPageResponse(
			page, size,
			pageable -> beanQueryService.findByRoasteryId(roastery.getId(), pageable),
			() -> beanQueryService.countByRoasteryId(roastery.getId()),
			this::getBeanResDtoWithFlavors
		);
	}

	public Page<BeanResDto> searchBeansByCountry(final String keyword, final int page, final int size) {
		return PageUtils.buildPageResponse(
			page, size,
			pageable -> beanQueryService.findByCountryContaining(keyword, pageable),
			() -> beanQueryService.countByCountryContaining(keyword),
			this::getBeanResDtoWithFlavors
		);
	}

	private BeanResDto getBeanResDtoWithFlavors(final Bean bean) {
		List<Flavor> flavors = beanFlavorMapper.findFlavorsByBeanId(bean.getId());
		return BeanConverter.toBeanResDto(bean, flavors);
	}
}
