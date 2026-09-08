common-redis
============

[![Maven Central](https://img.shields.io/badge/maven--central-5.3.1-blue.svg)](https://repo1.maven.org/maven2/org/bardframework/commons/common-redis/)
[![License](http://img.shields.io/:license-apache-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

`DataManager` — a small typed facade over `RedisTemplate` for the handful of operations application code actually needs.

Part of [**Bard Commons**](../README.md) · [Bard Framework](https://github.com/bardframework)

```xml
<dependency>
    <groupId>org.bardframework.commons</groupId>
    <artifactId>common-redis</artifactId>
    <version>5.3.1</version>
</dependency>
```

`RedisTemplate` is powerful and verbose. Most application code wants six things: put with a TTL, get,
put an object as JSON, read it back, delete, and check existence. `DataManager` is those, plus set
and hash operations.

```java
public interface DataManager {
    void        put(String key, String value, Duration expiration);
    String      get(String key);
    <T> T       getFromJson(String key, Class<T> clazz);
    void        putAsJson(String key, Object value, Duration expiration);
    boolean     remove(String key);
    boolean     isExist(String key);

    void        addToSet(String key, String value, Duration expiration);
    Set<String> getFromSet(String key);
    void        removeFromSet(String key, String value, Duration expiration);

    void        putAsMap(String key, Map<?, ?> map, Duration expiration);
    Object      getFromMap(String key, String hashKey);
    void        removeFromMap(String key, String hashKey, Duration expiration);
}
```

```java
@Bean
public DataManager dataManager(RedisTemplate<String, Object> template) {
    return new DataManagerRedisImpl(template);
}

dataManager.putAsJson("flow:" + token, flowData, Duration.ofMinutes(15));
FlowData restored = dataManager.getFromJson("flow:" + token, FlowData.class);
```

JSR-310 types are registered, so `LocalDate` and friends round-trip correctly.

Used by [`bard-flow-redis`](https://github.com/bardframework/bard-form-parent),
[`common-security-redis`](../common-security-redis) and [`common-waf`](../common-waf).

## License

Apache License 2.0.
