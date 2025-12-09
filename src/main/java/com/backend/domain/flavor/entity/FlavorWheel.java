package com.backend.domain.flavor.entity;

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
public class FlavorWheel {
	private Long id;
	private String code;
	private Long parentId;
	private Integer level;
	private String path;
	private String name;
	private String description;
	private String colorHex;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}

