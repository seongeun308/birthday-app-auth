package com.birthday.auth.repository;

import com.birthday.auth.domain.dto.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class RedisRefreshTokenRepository implements RefreshTokenRepository {

    private static final String PREFIX = "RT:";

    private final StringRedisTemplate redisTemplate;

    @Override
    public void save(Long accountId, Token token) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireAt = LocalDateTime.parse(token.getExpireAt());

        Duration duration = Duration.between(now, expireAt);

        redisTemplate.opsForValue().set(PREFIX + accountId, token.getToken(), duration);
    }

    @Override
    public boolean existsById(Long accountId) {
        return redisTemplate.opsForValue().get(PREFIX + accountId) != null;
    }
}
