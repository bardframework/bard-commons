common-security
===============

[![Maven Central](https://img.shields.io/badge/maven--central-5.6.1-blue.svg)](https://repo1.maven.org/maven2/org/bardframework/commons/common-security/)
[![License](http://img.shields.io/:license-apache-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

Opaque, server-side token authentication for Spring Security — revocable by design, with no readable payload.

Part of [**Bard Commons**](../README.md) · [Bard Framework](https://github.com/bardframework)

```xml
<dependency>
    <groupId>org.bardframework.commons</groupId>
    <artifactId>common-security</artifactId>
    <version>5.6.1</version>
</dependency>
```

## Why opaque tokens

A JWT cannot be revoked before it expires and its payload is readable by anyone holding it. For
applications that need instant logout, immediate permission changes, or session invalidation on
suspicion, a server-side token is simply the right tool. This module provides one, pluggable enough
that the storage and the transport are both your choice.

## The pieces

```
SecurityContextFilter                 servlet filter, first in the chain
        │
        ▼
SecurityContextManager<A, U>          where does the token travel?
   ├─ HeaderSecurityContextManagerAbstract     Authorization / custom header
   ├─ CookieSecurityContextManagerAbstract     cookie
   └─ RequestSecurityContextManagerAbstract    request parameter
        │
        ▼
TokenManager<A>                       where does the token live?
   ├─ TokenManagerInMemory                     single node, dev
   └─ TokenManagerRedis                        multi-node  → common-security-redis
        │
        ▼
SecurityContextHolder.getContext().setAuthentication(...)
```

An unrecognised token id is not merely ignored — it is deleted from the client (cookie cleared) and
the context is emptied, so a stale client stops resending it.

## Usage

```java
public class AppAuthentication extends AuthenticationAbstract<AppUser> { ... }

public class AppSecurityContextManager
        extends HeaderSecurityContextManagerAbstract<AppAuthentication, AppUser> {
    public AppSecurityContextManager(TokenManager<AppAuthentication> tokenManager) {
        super(tokenManager);
        setTokenName("Authorization");
    }
}
```

Login — put the authentication in and hand the id back to the client:

```java
String tokenId = securityContextManager.putAuthentication(authentication, response);
```

Logout:

```java
securityContextManager.clearSecurityContext(request, response);
```

Register `SecurityContextFilter` in the Spring Security chain and every subsequent request resolves
its user automatically.

## Types

| Type | Role |
| --- | --- |
| `Token` | The stored record: id, user, issue time. |
| `AuthenticationAbstract<U>` | Spring Security `Authentication` bound to your user type. |
| `TokenManager<T>` | `put` / `get` / `remove`. Implement it for any store. |
| `SecurityContextManager<A, U>` | Reads and writes the token on the transport; also `getUser(request)`. |

## See also

* [`common-security-redis`](../common-security-redis) — distributed token storage.
* [`common-waf`](../common-waf) — rate limiting keyed by the same bearer token.

## License

Apache License 2.0.
