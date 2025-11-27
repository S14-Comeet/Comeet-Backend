package com.backend.common.auth.redis.repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.backend.common.auth.redis.RefreshToken;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {

    private static final String KEY_PREFIX = "refreshToken:";
    private static final long TTL_DAYS = 3; // 3일

    private final RedisTemplate<String, Object> redisTemplate;


    public void save(RefreshToken refreshToken) {
        String key = KEY_PREFIX + refreshToken.getSocialId();
        redisTemplate.opsForValue().set(key, refreshToken, TTL_DAYS, TimeUnit.DAYS);
    }

    public Optional<RefreshToken> findById(String socialId) {
        String key = KEY_PREFIX + socialId;
        Object value = redisTemplate.opsForValue().get(key);

        if (value == null) {
            return Optional.empty();
        }

        if (value instanceof RefreshToken) {
            return Optional.of((RefreshToken) value);
        }

        return Optional.empty();
    }

    public void deleteById(final String socialId) {
        String key = KEY_PREFIX + socialId;
        redisTemplate.delete(key);
    }

    public boolean existsById(final String socialId) {
        String key = KEY_PREFIX + socialId;
        return redisTemplate.hasKey(key);
    }
}
