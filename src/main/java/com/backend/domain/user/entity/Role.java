package com.backend.domain.user.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Role {
	USER("ROLE_USER", "사용자"),
	OWNER("ROLE_OWNER", "가맹점주"),
	GUEST("ROLE_GUEST", "임시 사용자"),
	WITHDRAWN("ROLE_WITHDRAWN", "탈퇴한 사용자"),
	;

	private final String key;
	private final String description;

	public static boolean isNotActiveUser(final Role role) {
		return role == GUEST || role == WITHDRAWN;
	}
}
