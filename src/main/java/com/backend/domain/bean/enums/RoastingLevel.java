package com.backend.domain.bean.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum RoastingLevel {
	LIGHT("LIGHT", "라이트 로스팅"),
	MEDIUM_LIGHT("MEDIUM_LIGHT", "미디엄 라이트 로스팅"),
	MEDIUM("MEDIUM", "미디엄 로스팅"),
	MEDIUM_DARK("MEDIUM_DARK", "미디엄 다크 로스팅"),
	DARK("DARK", "다크 로스팅"),
	HEAVY("HEAVY", "헤비 로스팅"),
	;

	private final String key;
	private final String description;
}
