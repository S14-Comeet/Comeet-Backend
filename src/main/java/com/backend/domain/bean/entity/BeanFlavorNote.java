package com.backend.domain.bean.entity;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanFlavorNote {
	private Long id;
	private Long beanId;
	private Long flavorId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
