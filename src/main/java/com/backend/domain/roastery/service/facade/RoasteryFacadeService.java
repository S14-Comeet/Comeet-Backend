package com.backend.domain.roastery.service.facade;

import java.util.List;
import java.util.function.Function;
import java.util.function.IntSupplier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.RoasteryException;
import com.backend.common.util.PageUtils;
import com.backend.domain.roastery.converter.RoasteryConverter;
import com.backend.domain.roastery.dto.request.RoasteryCreateReqDto;
import com.backend.domain.roastery.dto.request.RoasteryUpdateReqDto;
import com.backend.domain.roastery.dto.response.RoasteryResDto;
import com.backend.domain.roastery.entity.Roastery;
import com.backend.domain.roastery.factory.RoasteryFactory;
import com.backend.domain.roastery.service.command.RoasteryCommandService;
import com.backend.domain.roastery.service.query.RoasteryQueryService;
import com.backend.domain.roastery.validator.RoasteryValidator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class RoasteryFacadeService {

	private final RoasteryQueryService roasteryQueryService;
	private final RoasteryCommandService roasteryCommandService;

	private final RoasteryValidator roasteryValidator;
	private final RoasteryFactory roasteryFactory;

	public RoasteryResDto createRoastery(final Long ownerId, final RoasteryCreateReqDto reqDto) {
		Roastery roastery = roasteryFactory.create(ownerId, reqDto);
		int affectedRows = roasteryCommandService.insert(roastery);
		if (affectedRows == 0) {
			throw new RoasteryException(ErrorCode.ROASTERY_SAVE_FAILED);
		}
		return RoasteryConverter.toRoasteryResDto(roastery);
	}

	public RoasteryResDto updateRoastery(final Long roasteryId, final Long userId, final RoasteryUpdateReqDto reqDto) {
		Roastery existingRoastery = roasteryQueryService.findById(roasteryId);
		roasteryValidator.validateOwnership(existingRoastery, userId);

		Roastery updatedRoastery = roasteryFactory.createForUpdate(existingRoastery, reqDto);
		int affectedRows = roasteryCommandService.update(updatedRoastery);
		if (affectedRows == 0) {
			throw new RoasteryException(ErrorCode.ROASTERY_UPDATE_FAILED);
		}
		return RoasteryConverter.toRoasteryResDto(updatedRoastery);
	}

	public RoasteryResDto getRoastery(final Long roasteryId) {
		Roastery roastery = roasteryQueryService.findById(roasteryId);
		return RoasteryConverter.toRoasteryResDto(roastery);
	}

	public Page<RoasteryResDto> getAllRoasteries(final int page, final int size) {
		return buildPageResponse(page, size,
			roasteryQueryService::findAll,
			roasteryQueryService::countAll
		);
	}

	public Page<RoasteryResDto> searchRoasteriesByName(final String keyword, final int page, final int size) {
		return buildPageResponse(page, size,
			pageable -> roasteryQueryService.findByNameContaining(keyword, pageable),
			() -> roasteryQueryService.countByNameContaining(keyword)
		);
	}

	public Page<RoasteryResDto> getMyRoasteries(final Long ownerId, final int page, final int size) {
		return buildPageResponse(page, size,
			pageable -> roasteryQueryService.findByOwnerId(ownerId, pageable),
			() -> roasteryQueryService.countByOwnerId(ownerId)
		);
	}

	private Page<RoasteryResDto> buildPageResponse(
		final int page,
		final int size,
		final Function<Pageable, List<Roastery>> finder,
		final IntSupplier counter
	) {
		Pageable pageable = PageUtils.getPageable(page, size);
		List<Roastery> roasteries = finder.apply(pageable);

		if (roasteries.isEmpty()) {
			return PageUtils.toPage(List.of(), pageable, 0);
		}

		int total = counter.getAsInt();
		List<RoasteryResDto> dtoList = roasteries.stream()
			.map(RoasteryConverter::toRoasteryResDto)
			.toList();

		return PageUtils.toPage(dtoList, pageable, total);
	}
}
