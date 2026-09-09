common-web
==========

[![Maven Central](https://img.shields.io/badge/maven--central-5.6.1-blue.svg)](https://repo1.maven.org/maven2/org/bardframework/commons/common-web/)
[![License](http://img.shields.io/:license-apache-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

Servlet-layer building blocks: a wildcard message source, consistent exception handling, cookie management and CORS.

Part of [**Bard Commons**](../README.md) · [Bard Framework](https://github.com/bardframework)

```xml
<dependency>
    <groupId>org.bardframework.commons</groupId>
    <artifactId>common-web</artifactId>
    <version>5.6.1</version>
</dependency>
```

## WildcardReloadableMessageSource

Standard Spring message sources require you to enumerate base names. In a codebase with a hundred
features that means one enormous `messages.properties`, or a hundred lines of configuration.

This subclass accepts **wildcards**, so each feature package keeps its translations next to its code:

```java
@Bean
public MessageSource messageSource() {
    WildcardReloadableMessageSource ms = new WildcardReloadableMessageSource();
    ms.addWildcardBaseNames("classpath*:**/i18n/**/*.properties");
    ms.addWildcardBaseNames("classpath*:**/i18n*.properties");
    ms.setDefaultEncoding("UTF-8");
    ms.setCacheSeconds(30);
    ms.setConcurrentRefresh(true);
    ms.setFallbackToSystemLocale(false);
    return ms;
}
```

```
app/customer/CustomerService.java
app/customer/i18n_fa.properties      ← found automatically
app/order/OrderService.java
app/order/i18n_fa.properties         ← found automatically
```

With `setCacheSeconds`, edits are picked up without a restart — which is what makes
[`bard-form`](https://github.com/bardframework/bard-form-parent) labels and behaviour tunable at
runtime.

## BaseExceptionControllerAdvice

An interface of `@ExceptionHandler` default methods. Implement it and get consistent error responses
without copying handlers between services:

```java
@RestControllerAdvice
public class AppExceptionAdvice implements BaseExceptionControllerAdvice { }
```

Covers `Exception`, `UnsupportedOperationException`, `MethodArgumentNotValidException`,
`HttpRequestMethodNotSupportedException`, unsupported media types, `AuthenticationException` and
`AccessDeniedException`.

## ExceptionHandlerFilter

`@ControllerAdvice` never runs for exceptions thrown *inside the filter chain* — an authentication
filter failure, a rate limiter rejection — so those escape as container error pages. This filter
catches them and renders them through your advice:

```java
@Bean
public FilterRegistrationBean<ExceptionHandlerFilter> exceptionHandlerFilter(
        AppExceptionAdvice advice, ObjectMapper objectMapper) {
    var bean = new FilterRegistrationBean<>(new ExceptionHandlerFilter(advice, objectMapper));
    bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return bean;
}
```

## CookieHandler

A cookie described once, then read and written by name:

```java
CookieHandler token = new CookieHandler("token", "/");
token.setHttpOnly(true);
token.setSecure(true);
token.setSameSite("Strict");
token.setMaxAge(3600);

token.addCookie(response, tokenId);            // or addCookie(response, value, maxAge)
String value = token.getValue(request);
token.removeCookie(response);
```

## CorsFilter

An explicit servlet CORS filter (mappings, origins, methods, headers, exposed headers, credentials,
max-age) — usually configured through
[`common-spring-boot`](../common-spring-boot)'s `bard.web.cors.*` properties rather than by hand.

## Also here

* `Calendar` — `JALALI` / `GREGORIAN` / `ISLAMIC`, the calendar preference passed to date formatting.
* `WebUtils` — request/response helpers (client IP behind proxies, user agent, body reading).
* `ResourceUtils` — classpath resource scanning with wildcard patterns.

## License

Apache License 2.0.
