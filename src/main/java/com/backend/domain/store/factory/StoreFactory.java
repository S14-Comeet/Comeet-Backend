package com.backend.domain.store.factory;

import java.math.BigDecimal;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

import com.backend.common.util.ObjectUtils;
import com.backend.common.util.TimeUtils;
import com.backend.domain.store.dto.request.StoreCreateReqDto;
import com.backend.domain.store.dto.request.StoreUpdateReqDto;
import com.backend.domain.store.entity.Store;
import com.backend.domain.store.validator.StoreValidator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreFactory {

	private static final BigDecimal INITIAL_AVERAGE_RATING = BigDecimal.ZERO;
	private static final int INITIAL_COUNT = 0;

	private final StoreValidator storeValidator;

	public Store create(final StoreCreateReqDto reqDto, final Long ownerId) {
		LocalTime[] times = TimeUtils.parseOpeningHours(reqDto.openingHours());
		Store store = Store.builder()
			.ownerId(ownerId)
			.roasteryId(reqDto.roasteryId())
			.name(reqDto.name())
			.description(reqDto.description())
			.address(reqDto.address())
			.latitude(reqDto.latitude())
			.longitude(reqDto.longitude())
			.phoneNumber(reqDto.phoneNumber())
			.category(reqDto.category())
			.thumbnailUrl(reqDto.thumbnailUrl())
			.openTime(TimeUtils.getOpenTime(times))
			.closeTime(TimeUtils.getCloseTime(times))
			.averageRating(INITIAL_AVERAGE_RATING)
			.reviewCount(INITIAL_COUNT)
			.visitCount(INITIAL_COUNT)
			.isClosed(false)
			.build();

		storeValidator.validate(store);
		return store;
	}

	public Store update(final Store store, final StoreUpdateReqDto reqDto) {
		LocalTime[] newTimes = TimeUtils.parseOpeningHours(reqDto.openingHours());

		Store updatedStore = Store.builder()
			.id(store.getId())
			.ownerId(store.getOwnerId())
			.roasteryId(store.getRoasteryId())
			.name(ObjectUtils.getOrDefault(reqDto.name(), store.getName()))
			.description(ObjectUtils.getOrDefault(reqDto.description(), store.getDescription()))
			.address(ObjectUtils.getOrDefault(reqDto.address(), store.getAddress()))
			.latitude(ObjectUtils.getOrDefault(reqDto.latitude(), store.getLatitude()))
			.longitude(ObjectUtils.getOrDefault(reqDto.longitude(), store.getLongitude()))
			.phoneNumber(ObjectUtils.getOrDefault(reqDto.phoneNumber(), store.getPhoneNumber()))
			.category(ObjectUtils.getOrDefault(reqDto.category(), store.getCategory()))
			.thumbnailUrl(ObjectUtils.getOrDefault(reqDto.thumbnailUrl(), store.getThumbnailUrl()))
			.openTime(ObjectUtils.getOrDefault(TimeUtils.getOpenTime(newTimes), store.getOpenTime()))
			.closeTime(ObjectUtils.getOrDefault(TimeUtils.getCloseTime(newTimes), store.getCloseTime()))
			.averageRating(store.getAverageRating())
			.reviewCount(store.getReviewCount())
			.visitCount(store.getVisitCount())
			.isClosed(ObjectUtils.getOrDefault(reqDto.isClosed(), store.isClosed()))
			.deletedAt(store.getDeletedAt())
			.createdAt(store.getCreatedAt())
			.updatedAt(store.getUpdatedAt())
			.build();

		storeValidator.validate(updatedStore);
		return updatedStore;
	}
}
