package com.backend.common.auth.redis.repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.backend.common.auth.redis.BlackList;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BlackListRepository {

	private static final String KEY_PREFIX = "blackList:";
	private static final long TTL_DAYS = 3; // 3일

	private final RedisTemplate<String, Object> redisTemplate;

	public void save(final BlackList blackList) {
		String key = KEY_PREFIX + blackList.getId();
		redisTemplate.opsForValue().set(key, blackList, TTL_DAYS, TimeUnit.DAYS);
	}

	public Optional<BlackList> findById(final String socialId) {
		String key = KEY_PREFIX + socialId;
		Object value = redisTemplate.opsForValue().get(key);

		if (value == null) {
			return Optional.empty();
		}

		if (value instanceof BlackList) {
			return Optional.of((BlackList)value);
		}

		return Optional.empty();
	}

	public boolean existsById(String refreshToken) {
		String key = KEY_PREFIX + refreshToken;
		return redisTemplate.hasKey(key);
	}
}
