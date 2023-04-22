package org.bardframework.commons.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DataManagerRedisImpl implements DataManager {

    private static final String ERROR_WRITE_VALUE = "error writing value";

    protected final RedisTemplate<String, Object> redisTemplate;

    /**
     * must not failed on unknown properties
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DataManagerRedisImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean isExist(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    @Override
    public void putAsJson(String key, Object value, int expiration, TimeUnit unit) {
        try {
            String valueString = objectMapper.writeValueAsString(value);
            this.redisTemplate.opsForValue().set(key, valueString, expiration, unit);
        } catch (Exception e) {
            log.error("error putting [{}] to redis", value);
            throw new IllegalArgumentException("error writing value", e);
        }
    }

    @Override
    public void put(String key, String value, int expiration, TimeUnit unit) {
        try {
            this.redisTemplate.opsForValue().set(key, value, expiration, unit);
        } catch (Exception e) {
            log.error("error putting [{}] to redis", key);
            throw new IllegalArgumentException("error writing value", e);
        }
    }

    @Override
    public String get(String key) {
        return (String) this.redisTemplate.opsForValue().get(key);
    }

    @Override
    public <T> T getFromJson(String tokenId, Class<T> clazz) {
        try {
            String valueString = (String) this.redisTemplate.opsForValue().get(tokenId);
            if (null == valueString) {
                return null;
            }
            return objectMapper.readValue(valueString, clazz);
        } catch (Exception e) {
            log.error("error getting value with key [{}] from redis server, and converting to [{}]", tokenId, clazz);
            throw new IllegalArgumentException("error getting value from server", e);
        }
    }

    @Override
    public boolean remove(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    @Override
    public void addToSet(String key, String value, Long age, TimeUnit ageUnit) {
        try {
            this.redisTemplate.opsForSet().add(key, value);
            this.redisTemplate.persist(key);
            this.redisTemplate.expire(key, age, ageUnit);
        } catch (Exception e) {
            log.error("error putting [{}] to redis", key);
            throw new IllegalArgumentException(ERROR_WRITE_VALUE, e);
        }
    }

    @Override
    public Set<String> getFromSet(String key) {
        try {
            return (Set) this.redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error("error reading [{}] to redis", key);
            throw new IllegalArgumentException(ERROR_WRITE_VALUE, e);
        }
    }

    @Override
    public void removeFromSet(String key, String value, Long age, TimeUnit ageUnit) {
        try {
            this.redisTemplate.opsForSet().remove(key, value);
            this.redisTemplate.persist(key);
            this.redisTemplate.expire(key, age, ageUnit);
        } catch (Exception e) {
            log.error("error removing set value of [{}] from redis", key);
            throw new IllegalArgumentException(ERROR_WRITE_VALUE, e);
        }
    }

    @Override
    public void putAsMap(String key, Map<?, ?> map, Long age, TimeUnit ageUnit) {
        try {
            this.redisTemplate.opsForHash().putAll(key, map);
            this.redisTemplate.persist(key);
            this.redisTemplate.expire(key, age, ageUnit);
        } catch (Exception e) {
            log.error("error putting [{}] to redis", key);
            throw new IllegalArgumentException(ERROR_WRITE_VALUE, e);
        }
    }

    @Override
    public void putAsMap(String key, AsMapSerializer object, Long age, TimeUnit ageUnit) {
        this.putAsMap(key, object.getAsMap(), age, ageUnit);
    }

    @Override
    public <T extends AsMapDeserializer> T getFromMap(String key, Class<T> clazz) {
        try {
            Map<Object, Object> map = this.redisTemplate.opsForHash().entries(key);
            if (map.isEmpty()) {
                return null;
            }
            T object = clazz.newInstance();
            object.init(map);
            return object;
        } catch (Exception e) {
            log.error("error getting value with key [{}] from redis server", key);
            throw new IllegalArgumentException("error getting value from server", e);
        }
    }

    @Override
    public Object getFromMap(String key, String hashKey) {
        try {
            return this.redisTemplate.opsForHash().get(key, hashKey);
        } catch (Exception e) {
            log.error("error getting value with key [{}] from redis server", key);
            throw new IllegalArgumentException("error getting value from server", e);
        }
    }

    @Override
    public void removeFromMap(String userId, String topic, Long age, TimeUnit ageUnit) {
        try {
            this.redisTemplate.opsForHash().delete(userId, topic);
            this.redisTemplate.expire(userId, age, ageUnit);
        } catch (Exception e) {
            log.error("error removing map key of [{}] from redis", userId);
            throw new IllegalArgumentException("error writing value", e);
        }
    }
}
