package org.bardframework.commons.redis;

import java.time.Duration;
import java.util.Map;
import java.util.Set;

public interface DataManager {

    void put(String key, String value, Duration expiration);

    String get(String key);

    <T> T getFromJson(String tokenId, Class<T> clazz);

    void putAsJson(String key, Object value, Duration expiration);

    boolean remove(String key);

    boolean isExist(String key);

    void addToSet(String key, String value, Duration expiration);

    Set<String> getFromSet(String key);

    void removeFromSet(String key, String value, Duration expiration);

    void putAsMap(String key, Map<?, ?> map, Duration expiration);

    Object getFromMap(String key, String hashKey);

    void removeFromMap(String userId, String topic, Duration expiration);
}
