common-security-redis
=====================

[![Maven Central](https://img.shields.io/badge/maven--central-5.6.1-blue.svg)](https://repo1.maven.org/maven2/org/bardframework/commons/common-security-redis/)
[![License](http://img.shields.io/:license-apache-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

Redis-backed `TokenManager` — the same authentication model as [`common-security`](../common-security), shared across nodes and surviving restarts.

Part of [**Bard Commons**](../README.md) · [Bard Framework](https://github.com/bardframework)

```xml
<dependency>
    <groupId>org.bardframework.commons</groupId>
    <artifactId>common-security-redis</artifactId>
    <version>5.6.1</version>
</dependency>
```

`TokenManagerInMemory` is fine on one node. The moment you run two, a user authenticated against node
A is anonymous on node B unless you pin sessions. This module removes that constraint.

```java
@Bean
public TokenManager<AppAuthentication> tokenManager(RedisTemplate<String, Object> template) {
    return new TokenManagerRedis<AppAuthentication>(template, Duration.ofHours(8)) { };
}
```

`TokenManagerRedis<T extends Token>` is abstract — extend it to bind your own `Token` subtype. The
TTL is applied per entry, so an idle session expires without a cleanup job, and everything else about
[`common-security`](../common-security) stays identical.

Depends on `common-security` and Spring Data Redis.

## License

Apache License 2.0.
