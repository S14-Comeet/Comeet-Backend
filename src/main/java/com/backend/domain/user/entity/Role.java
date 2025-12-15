package com.backend.domain.user.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Role {
	USER("ROLE_USER", "사용자", false),
	STORE_MANAGER("ROLE_STORE_MANAGER", "매장 관리자", true),
	ROASTERY_MANAGER("ROLE_ROASTERY_MANAGER", "로스터리 관리자", true),
	GUEST("ROLE_GUEST", "임시 사용자", false),
	WITHDRAWN("ROLE_WITHDRAWN", "탈퇴한 사용자", false),
	;

	private final String key;
	private final String description;
	private final boolean manager;

	public static boolean isNotActiveUser(final Role role) {
		return role == GUEST || role == WITHDRAWN;
	}

	public static boolean isManager(final Role role) {
		return role.manager;
	}

}
