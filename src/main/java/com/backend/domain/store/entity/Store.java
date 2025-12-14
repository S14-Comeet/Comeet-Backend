package com.backend.domain.store.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Store {
	private Long id;
	private Long roasteryId;
	private Long ownerId;
	private String name;
	private String description;
	private String address;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private String phoneNumber;
	private String category;
	private String thumbnailUrl;
	private LocalTime openTime;
	private LocalTime closeTime;
	private BigDecimal averageRating;
	private Integer reviewCount;
	private Integer visitCount;
	private boolean isClosed;
	private LocalDateTime deletedAt;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public static Store of(final Long id, final Long roasteryId, final String name,
		final String address, final BigDecimal latitude, final BigDecimal longitude) {
		return Store.builder()
			.id(id)
			.roasteryId(roasteryId)
			.name(name)
			.address(address)
			.latitude(latitude)
			.longitude(longitude)
			.averageRating(BigDecimal.ZERO)
			.reviewCount(0)
			.visitCount(0)
			.isClosed(false)
			.build();
	}

}
