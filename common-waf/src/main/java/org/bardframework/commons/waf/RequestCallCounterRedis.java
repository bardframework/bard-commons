package org.bardframework.commons.waf;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class RequestCallCounterRedis implements RequestCallCounter {

    private final RedisTemplate<String, String> redisTemplate;


    public RequestCallCounterRedis(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Long increment(String key) {
        return redisTemplate.opsForValue().increment(key, 1);
    }

    @Override
    public void expire(String key, int expiration, TimeUnit unit) {
        redisTemplate.expire(key, expiration, unit);
    }
}
