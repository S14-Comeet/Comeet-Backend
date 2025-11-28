package com.backend.common.auth.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {
	private String socialId;
	private String token;
	private Long expirationTime;

	public void updateToken(String newToken) {
		this.token = newToken;
	}
}
