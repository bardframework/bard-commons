common-spring-boot
==================

[![Maven Central](https://img.shields.io/badge/maven--central-6.1.1-blue.svg)](https://repo1.maven.org/maven2/org/bardframework/commons/common-spring-boot/)
[![License](http://img.shields.io/:license-apache-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

Two Spring Boot conveniences: a startup dump of the fully resolved configuration, and property-driven CORS.

Part of [**Bard Commons**](../README.md) · [Bard Framework](https://github.com/bardframework)

```xml
<dependency>
    <groupId>org.bardframework.commons</groupId>
    <artifactId>common-spring-boot</artifactId>
    <version>6.1.1</version>
</dependency>
```

## ConfigsConfiguration — see what actually got loaded

"Why is this property not taking effect?" is usually answered by discovering that some other property
source overrode it. `ConfigsLogger` prints, once at startup, every resolved property together with
its source, plus every i18n resource that was discovered.

Keys containing `password`, `secret`, `credential` or `token` are redacted, so the dump is safe to
keep in production logs.

```java
@Configuration
@Import(ConfigsConfiguration.class)
public class AppConfiguration { }
```

It expects an `appConfigs` (`Map<String, String>`) and an `i18nResources` (`List<Resource>`) bean —
the same resource list your `MessageSource` uses, so the log shows exactly which translation files
were found. Missing translations are usually a resource-discovery problem, and this is where you see
it.

## CorsConfiguration — CORS from properties

```properties
bard.web.cors.enabled=true
bard.web.cors.mapping=/api/**
bard.web.cors.allowed-origins=https://app.example.com
bard.web.cors.allowed-methods=GET,POST,PUT,PATCH,DELETE
bard.web.cors.allowed-headers=Content-Type,Authorization,X-Flow-Token
bard.web.cors.exposed-headers=X-Flow-Token,X-Trace-Id
bard.web.cors.allow-credentials=true
bard.web.cors.max-age-second=3600
```

Registers [`common-web`](../common-web)'s `CorsFilter` near the front of the chain, and only when
`bard.web.cors.enabled=true` — so an environment that does not need CORS does not get it by accident.

> If you use [`bard-flow`](https://github.com/bardframework/bard-form-parent) from a browser, remember
> to allow **and expose** `X-Flow-Token`; without the exposure the client cannot read the token it is
> supposed to send back.

## License

Apache License 2.0.
