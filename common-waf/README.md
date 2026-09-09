common-waf
==========

[![Maven Central](https://img.shields.io/badge/maven--central-6.1.1-blue.svg)](https://repo1.maven.org/maven2/org/bardframework/commons/common-waf/)
[![License](http://img.shields.io/:license-apache-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

Per-key request rate limiting as a servlet filter — a small web application firewall you configure in properties rather than in front of the application.

Part of [**Bard Commons**](../README.md) · [Bard Framework](https://github.com/bardframework)

```xml
<dependency>
    <groupId>org.bardframework.commons</groupId>
    <artifactId>common-waf</artifactId>
    <version>6.1.1</version>
</dependency>
```

## What it does

Declare rules of the form *"at most N calls to this path, per key, per period"*. What counts as a
"key" is pluggable — the client IP, the bearer token, a header, a cookie or a request parameter — so
the same mechanism protects a login endpoint per IP and a paid API per token.

## Configuration

```properties
# login: 10 attempts per IP per minute
waf.rules[0].path=/api/auth/login
waf.rules[0].http-method=POST
waf.rules[0].limit=10
waf.rules[0].period=1
waf.rules[0].period-unit=MINUTES

# OTP requests: 5 per hour, everything under the path
waf.rules[1].path=/api/signup/**
waf.rules[1].limit=5
waf.rules[1].period=1
waf.rules[1].period-unit=HOURS
```

Import `WafConfiguration` and supply the two collaborators:

```java
@Bean RequestKeyDetector requestKeyDetector() { return new IpDetector(); }
@Bean RequestCallCounter requestCallCounter(RedisTemplate<String, String> redisTemplate) {
    return new RequestCallCounterRedis(redisTemplate);
}
```

`WafConfiguration` builds a `WafFilter` from the rules and injects the detector and counter into each.

## Choosing the key

| `RequestKeyDetector` | Limits per |
| --- | --- |
| `IpDetector` | Client IP. Pass a list of header names (`X-Forwarded-For`, …) to trust when behind a proxy. |
| `BearerTokenDetector` | Authenticated caller. |
| `RequestHeaderKeyDetector` | A named header — API key, device id. |
| `RequestCookieKeyDetector` | A named cookie. |
| `RequestParamKeyDetector` | A named query parameter. |

Implement `RequestKeyDetector` for anything else. Returning `null` means "cannot identify" — the
request is logged and allowed through rather than blocked, so a misconfigured detector degrades
safely.

## Choosing the counter

| `RequestCallCounter` | Use when |
| --- | --- |
| `RequestCallCounterInMemory` | Single node, development. |
| `RequestCallCounterRedis` | Multiple nodes — counters must be shared or each node grants the full quota. |

## Behaviour

Exceeding a rule throws `CallLimitExceedException`. Each rule carries a `whiteList` of keys that
bypass it — useful for monitoring probes and internal callers.

Rate limiting protects the *entry* to a flow; once inside,
[`bard-flow`](https://github.com/bardframework/bard-form-parent) enforces its own per-conversation
limits on OTP sends and attempts. The two are complementary.

## License

Apache License 2.0.
