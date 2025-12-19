package com.backend.domain.passport.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Passport {
	private Long id;
	private Long userId;
	private Integer year;
	private Integer month;
	private String coverImageUrl;
	private Integer totalCoffeeCount;
	private Integer totalStoreCount;
	private Integer totalBeanCount;
	private String topOrigin;
	private String topRoastery;
	private String originSequence;
	private BigDecimal totalOriginDistance;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
