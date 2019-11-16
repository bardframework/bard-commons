package org.bardframework.commons.redis;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface DataManager {

    void put(String key, String value, int expiration, TimeUnit unit);

    String get(String key);

    <T> T getFromJson(String tokenId, Class<T> clazz);

    void putAsJson(String key, Object value, int expiration, TimeUnit unit);

    boolean remove(String key);

    boolean isExist(String key);

    void addToSet(String key, String value, Long age, TimeUnit ageUnit);

    Set<String> getFromSet(String key);

    void removeFromSet(String key, String value, Long age, TimeUnit ageUnit);

    void putAsMap(String key, Map<?, ?> map, Long age, TimeUnit ageUnit);

    void putAsMap(String key, AsMapSerializer object, Long age, TimeUnit ageUnit);

    <T extends AsMapDeserializer> T getFromMap(String key, Class<T> clazz);

    Object getFromMap(String key, String hashKey);

    void removeFromMap(String userId, String topic, Long age, TimeUnit ageUnit);
}
