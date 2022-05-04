package org.bardframework.commons.security.token.manager;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public abstract class TokenManagerRedis<T extends Token> implements TokenManager<T> {

    /**
     * millisecond token expire time
     */
    protected final long tokenExpirationMills;
    private final RedisTemplate<String, T> redisTemplate;

    protected TokenManagerRedis(RedisTemplate<String, T> redisTemplate, long tokenExpirationMills) {
        this.redisTemplate = redisTemplate;
        this.tokenExpirationMills = tokenExpirationMills;
    }

    @Override
    public String put(T token) {
        String tokenId = UUID.randomUUID() + "-" + System.nanoTime();
        this.getRedisTemplate().opsForValue().set(tokenId, token, this.getTokenExpirationMills(), TimeUnit.MILLISECONDS);
        this.getRedisTemplate().persist(tokenId);
        return tokenId;
    }

    @Override
    public T get(String tokenId) {
        return this.getRedisTemplate().opsForValue().get(tokenId);
    }

    @Override
    public boolean remove(String tokenId) {
        return this.getRedisTemplate().delete(tokenId) == Boolean.TRUE;
    }

    public RedisTemplate<String, T> getRedisTemplate() {
        return redisTemplate;
    }

    public long getTokenExpirationMills() {
        return tokenExpirationMills;
    }
}
