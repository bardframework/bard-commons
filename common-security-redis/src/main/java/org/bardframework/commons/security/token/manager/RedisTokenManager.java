package org.bardframework.commons.security.token.manager;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public abstract class RedisTokenManager<T extends Token> implements TokenManager<T> {

    private RedisTemplate<String, T> redisTemplate;
    /**
     * millisecond token expire time
     */
    private long tokenExpirationMills = 10 * 60 * 1000;

    @Override
    public String put(T token) {
        String tokenId = UUID.randomUUID().toString() + "-" + System.nanoTime();
        redisTemplate.opsForValue().set(tokenId, token, tokenExpirationMills, TimeUnit.MILLISECONDS);
        redisTemplate.persist(tokenId);
        return tokenId;
    }

    @Override
    public T get(String tokenId) {
        return redisTemplate.opsForValue().get(tokenId);
    }

    @Override
    public boolean remove(String tokenId) {
        return redisTemplate.delete(tokenId) == Boolean.TRUE;
    }


    public void setRedisTemplate(RedisTemplate<String, T> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setTokenExpirationMills(long tokenExpirationMills) {
        this.tokenExpirationMills = tokenExpirationMills;
    }
}
