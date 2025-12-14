package com.backend.domain.bean.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum RoastingLevel {
	LIGHT("LIGHT", "라이트 로스팅"),
	MEDIUM("MEDIUM", "미디엄 로스팅"),
	HEAVY("HEAVY", "헤비 로스팅"),
	;

	private final String key;
	private final String description;
}
