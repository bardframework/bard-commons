Bard Commons
============

[![Maven Central](https://img.shields.io/badge/maven--central-5.3.1-blue.svg)](https://repo1.maven.org/maven2/org/bardframework/commons/)
[![License](http://img.shields.io/:license-apache-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

Fifteen small, independently consumable libraries that the rest of [Bard Framework](https://github.com/bardframework)
is built on. Each is its own artifact so you only pull in what you use — `common-utils` has no Spring
dependency at all, and nothing here forces you to adopt the rest of the framework.

`groupId` is `org.bardframework.commons` for every artifact, version **5.3.1** (managed by
[`bard-bom`](https://github.com/bardframework/bard-bom)).

```xml
<dependency>
    <groupId>org.bardframework.commons</groupId>
    <artifactId>common-utils</artifactId>
</dependency>
```

## Artifacts at a glance

| Artifact | Depends on | Purpose |
| --- | --- | --- |
| [`common-utils`](#common-utils) | Apache Commons only | Reflection, dates, crypto, images, HTTP client, ISO code enums. |
| [`common-utils-persian`](#common-utils-persian) | `common-utils`, `jalali-date` | Persian/Arabic text normalisation, Iranian national-ID validation. |
| [`common-jackson`](#common-jackson) | Jackson, Spring | Reusable serializers/deserializers and a converter framework. |
| [`common-web`](#common-web) | Spring Web/Security | Message source, cookies, CORS, exception advice, filters. |
| [`common-security`](#common-security) | Spring Security | Opaque-token authentication: manager, context, filter. |
| [`common-security-redis`](#common-security-redis) | `common-security`, Redis | Redis-backed token store. |
| [`common-redis`](#common-redis) | Spring Data Redis | `DataManager` — a small typed facade over `RedisTemplate`. |
| [`common-waf`](#common-waf) | Spring Security | Per-key request rate limiting (a mini web application firewall). |
| [`common-captcha`](#common-captcha) | `common-utils-persian` | Image captcha generator, Latin/Persian/Arabic alphabets. |
| [`common-sms`](#common-sms) | `common-utils` | `SmsSender` abstraction with a template-driven HTTP implementation. |
| [`common-spring-boot`](#common-spring-boot) | Spring Boot | Startup config dump and CORS auto-configuration. |
| [`common-swagger`](#common-swagger) | springdoc-openapi | One-property OpenAPI/Swagger UI setup, secured by default. |
| [`common-monitoring`](#common-monitoring) | Actuator, Micrometer | Prometheus endpoint, `/actuator/**` denied unless explicitly enabled. |
| [`common-opentelemetry`](#common-opentelemetry) | OTel, Actuator | Tracing, logback appender, trace-id filter. |
| [`common-apm`](#common-apm) | Elastic APM | Programmatic self-attach of the Elastic APM agent. |

---

## common-utils

Pure Java plus Apache Commons — usable outside Spring entirely.

| Class | Highlights |
| --- | --- |
| `ReflectionUtils` | `getGenericArgType(clazz, index)` — resolves a generic type argument at runtime, the mechanism most Bard base classes use to discover their own model/criteria/DTO types. Also `getPropertyValue`, `newInstance`, `getSupersOf`. |
| `DateTimeUtils` | Conversions between epoch, `LocalDate(Time)` and `Instant`. |
| `DigestUtils`, `EncryptionUtil`, `EncodingUtils`, `BytesUtils` | Hashing, symmetric encryption, Base64/hex. |
| `StringTemplateUtils` | `fillTemplate(text, args)` — the placeholder substitution used by every form/table label. |
| `HttpCaller` / `HttpCallRequest` / `HttpCallResponse` | Small templated HTTP client: URL template, method, headers and body are all filled from a `Map`. Base class for `SmsSenderHttpCall` and the flow's HTTP data provider. |
| `ImageUtils`, `MoneyUtils`, `NumberUtils`, `IpUtils`, `UrlUtils`, `OsUtils`, `JarFileUtils`, `CharsetUtils`, `SerializationUtils` | Focused helpers. |
| `AssertionUtils` | `notNull` / `notEmpty` / `isNotBlank` guards used throughout the framework. |
| `data/*Code` | Generated enums for ISO country, language (alpha-2/alpha-3), locale, script and currency codes. |

```java
Class<M> modelClass = ReflectionUtils.getGenericArgType(getClass(), 0);
String greeting = StringTemplateUtils.fillTemplate("Hello ${name}", Map.of("name", "Ali"));
```

## common-utils-persian

| Class | Purpose |
| --- | --- |
| `PersianStringUtils` | Normalise Arabic characters to Persian (`ي`→`ی`, `ك`→`ک`), digits, and remove RLO/LRO control characters. |
| `IranianNationalNoUtils` | Validate and generate Iranian national IDs (check-digit algorithm). |
| `LetterConverterUtility` | Shape Persian text for rendering into images (used by the captcha generator). |
| `UtilityMethods` | Number-to-Persian-words and related conversions. |
| Jackson converters | `ArabicToPersianDeserializer`, `NormalPersianNumberDeserializer`, `PersianStringDisinfectant` — sanitise incoming text at the deserialisation boundary rather than in every service. |

## common-jackson

A `Converter`-based bridge so Spring `Converter` implementations can act as Jackson
serializers/deserializers (`ConverterSerializer`, `ConverterDeserializer`), plus a ready set:

`LocalDate(Time)ToLong`, `LongToLocalDate(Time)`, `LocalDate(Time)ToString`, `StringToByte/Short/
Integer/Long/Double/Boolean`, `StringToByteArray`, `ByteArrayToString`, `StringToEnumSerializer`,
`JsonRloRemoverHtmlScapeDeserializer` (strips bidi-override characters and HTML-escapes on input).

## common-web

| Class | Purpose |
| --- | --- |
| `WildcardReloadableMessageSource` | `ReloadableResourceBundleMessageSource` that accepts **wildcard base names**, e.g. `classpath*:**/i18n*.properties`. This is what lets each feature package keep its own translation file next to its code instead of one giant `messages.properties`. |
| `BaseExceptionControllerAdvice` | Interface with `@ExceptionHandler` default methods for `Exception`, `MethodArgumentNotValidException`, `AuthenticationException`, `AccessDeniedException`, unsupported method/media type… Implement it in your `@RestControllerAdvice` and get consistent error responses. |
| `CookieHandler` | Declarative cookie definition (name, path, domain, max-age, `secure`, `httpOnly`, `sameSite`) with read/write/delete helpers. |
| `CorsFilter` | Servlet CORS filter driven by explicit lists rather than annotations. |
| `ExceptionHandlerFilter` | Catches exceptions thrown *inside the filter chain*, where `@ControllerAdvice` never runs. |
| `WebUtils`, `ResourceUtils` | Request/response helpers and classpath resource scanning. |

```java
@Bean
public MessageSource messageSource() {
    WildcardReloadableMessageSource ms = new WildcardReloadableMessageSource();
    ms.addWildcardBaseNames("classpath*:**/i18n*.properties");
    ms.setDefaultEncoding("UTF-8");
    ms.setCacheSeconds(30);
    return ms;
}
```

## common-security

Opaque server-side tokens — deliberately *not* JWT, so a session can be revoked instantly and
carries no readable payload.

```
SecurityContextFilter
        │  reads the token from a header / cookie / request parameter
        ▼
SecurityContextManager<A, U>            ← Header…, Cookie…, RequestSecurityContextManagerAbstract
        │  looks the token id up
        ▼
TokenManager<A>                          ← TokenManagerInMemory, or Redis (common-security-redis)
        │
        ▼
SecurityContextHolder.setAuthentication(...)
```

Implement `AuthenticationAbstract<U>` for your user type, pick a `SecurityContextManager` subclass
for where the token travels, and register `SecurityContextFilter`. An unknown token id is destroyed
and the context cleared.

## common-security-redis

`TokenManagerRedis<T extends Token>` — the same `TokenManager` contract backed by Redis, so tokens
survive restarts and are shared across nodes. It is abstract: extend it to bind your own `Token`
subtype and key prefix.

## common-redis

`DataManager` / `DataManagerRedisImpl`: `put`/`get` with TTL, `putAsJson`/`getFromJson`, plus set and
hash operations. Used by the flow store, the Redis token manager and the WAF counter.

## common-waf

Per-key request rate limiting as a servlet filter, configured from properties:

```properties
waf.rules[0].path=/api/auth/**
waf.rules[0].http-method=POST
waf.rules[0].limit=10
waf.rules[0].period=1
waf.rules[0].period-unit=MINUTES
```

* `RequestKeyDetector` decides *what* is being limited — `IpDetector`, `BearerTokenDetector`,
  `RequestHeaderKeyDetector`, `RequestCookieKeyDetector`, `RequestParamKeyDetector`.
* `RequestCallCounter` decides *where the counters live* — `RequestCallCounterInMemory` or
  `RequestCallCounterRedis`.
* Exceeding a rule throws `CallLimitExceedException`; a `whiteList` per rule bypasses it.

## common-captcha

`CaptchaService.generateCaptcha(CaptchaType type, int maxChars)` returns a `GeneratedCaptcha` — the
expected answer plus the rendered PNG bytes.
`CaptchaType` covers `ENGLISH_CHAR`, `ENGLISH_NUMBER`, `ENGLISH_NUMBER_CHAR`, `PERSIAN_CHAR`,
`PERSIAN_NUMBER_CHAR`, `PERSIAN_NUMBER_TEXT`, `ARABIC_CHAR`, `ARABIC_NUMBER_CHAR`. Fonts, character
sets and RTL handling are configurable per type.

## common-sms

```java
public interface SmsSender {
    boolean send(Map<String, Object> args) throws IOException;
    default boolean send(String to, String message, Map<String, Object> args) { ... }
}
```

* `SmsSenderHttpCall` — configure a URL template, HTTP method, headers and a success regex; works
  with most Iranian and international SMS gateways without writing code.
* `SmsSenderRanked` — holds a list of senders and delegates to the one that ranks highest under an
  overridable `comparator()` (subclass it to rank by cost, quota or health). It selects a single
  sender per call; it does not retry the others on failure.
* `SmsSenderNoOp` — logs a warning and reports success. Use in dev.

Phone numbers are masked in logs.

## common-spring-boot

* `ConfigsConfiguration` — logs the fully resolved configuration at startup (every property source
  and every discovered i18n resource), with values for keys containing `password`, `secret`,
  `credential` or `token` redacted. Invaluable when a property is being overridden and you cannot
  tell by whom.
* `CorsConfiguration` — registers `CorsFilter` from `bard.web.cors.*` properties, active only when
  `bard.web.cors.enabled=true`.

## common-swagger

Add the dependency and set `bard.web.swagger.enabled=true`. You get a `public-api` group, an
`OpenAPI` bean with bearer-token security, and a `SecurityFilterChain` that **denies**
`/swagger-ui/**` and `/v3/api-docs/**` unless enabled — so production is closed by default. Every
bean is `@ConditionalOnMissingBean`, so define your own `Info`/`OpenAPI`/`GroupedOpenApi` to
override.

## common-monitoring

Micrometer + Prometheus registry, and a `SecurityFilterChain` for `/actuator/**` that is `denyAll()`
unless `bard.web.monitoring.enabled=true`.

## common-opentelemetry

`OpenTelemetryConfiguration` plus `TraceIdFilter`, which puts the current trace id into the response
and the logging MDC so an application log line can be joined to a distributed trace. Ships the
OpenTelemetry logback appender.

## common-apm

Attaches the Elastic APM agent to the running JVM programmatically (`PROGRAMMATIC_SELF_ATTACH`) so no
`-javaagent` flag is needed:

```properties
bard.apm.elastic.enabled=true
bard.apm.elastic.elastic.server_url=http://apm:8200
bard.apm.elastic.elastic.service_name=my-service
```

Everything under `bard.apm.elastic.elastic.*` is passed straight to the agent.

## Building

```bash
mvn clean install
```

Java 21 required.

## License

Apache License 2.0.
