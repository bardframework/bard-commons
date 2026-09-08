common-utils
============

[![Maven Central](https://img.shields.io/badge/maven--central-5.3.1-blue.svg)](https://repo1.maven.org/maven2/org/bardframework/commons/common-utils/)
[![License](http://img.shields.io/:license-apache-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

Framework-agnostic Java utilities. Apache Commons is the only dependency — no Spring, no Jackson, no servlet API. This is the base every other Bard module builds on.

Part of [**Bard Commons**](../README.md) · [Bard Framework](https://github.com/bardframework)

```xml
<dependency>
    <groupId>org.bardframework.commons</groupId>
    <artifactId>common-utils</artifactId>
    <version>5.3.1</version>
</dependency>
```

## Reflection

`ReflectionUtils` is the most important class here — it is what lets Bard's base classes discover
their own type parameters instead of asking you to pass `Class` objects around.

```java
// inside a generic base class: which Model / Criteria / Dto did the subclass bind?
Class<M> modelClass    = ReflectionUtils.getGenericArgType(getClass(), 0);
Class<C> criteriaClass = ReflectionUtils.getGenericArgType(getClass(), 1);

Object value = ReflectionUtils.getPropertyValue(model, "owner.company.title");  // nested paths
M instance   = ReflectionUtils.newInstance(modelClass);
List<Class<?>> supers = ReflectionUtils.getSupersOf(clazz, true, true, false);
```

## Text and templates

```java
StringTemplateUtils.fillTemplate("Hello ::name::, you have ::count:: messages",
                                 Map.of("name", "Ali", "count", 3));

// or with your own delimiters
StringTemplateUtils.fillTemplate("Hello ${name}", "${", "}", Map.of("name", "Ali"));
```

The default delimiter is `::name::`. An unknown placeholder is left in place rather than blanked, so
a typo is visible instead of silently producing empty text.

Every form label, table header and message body in Bard goes through this, which is why any
configured text can carry placeholders resolved from the current data map.

## HTTP

A small templated HTTP client — the URL, method, headers and body are templates filled from a `Map`,
so an integration becomes configuration rather than code.

```java
HttpCaller caller = new HttpCaller("POST", "https://api.example.com/send?to=::to::&text=::message::");
HttpCallResponse response = caller.call(headers, Map.of("to", "0912…", "message", "…"));
```

`SmsSenderHttpCall` ([`common-sms`](../common-sms)) and the flow engine's HTTP data provider are both
built on it.

## Everything else

| Class | Purpose |
| --- | --- |
| `AssertionUtils` | `notNull`, `notEmpty`, `hasText`, `isNotBlank` guards with meaningful messages. |
| `DateTimeUtils` | Conversions between epoch millis, `LocalDate`, `LocalDateTime` and `Instant`. |
| `DigestUtils` | Hashing (MD5/SHA family) over strings, bytes and streams. |
| `EncryptionUtil` | Symmetric encrypt/decrypt helpers. |
| `EncodingUtils`, `BytesUtils`, `CharsetUtils` | Base64/hex, byte manipulation, charset detection. |
| `ImageUtils` | Resize, crop, re-encode, read dimensions. |
| `MoneyUtils`, `NumberUtils` | Currency formatting and numeric parsing helpers. |
| `IpUtils` | IPv4/IPv6 parsing, range checks, long↔string conversion. |
| `UrlUtils` | Build and manipulate URLs and query strings. |
| `OsUtils`, `JarFileUtils` | Platform detection; read resources out of jars. |
| `SerializationUtils` | Java serialization round-trips. |

## ISO code enums

Generated enums under `org.bardframework.commons.utils.data`, each with the official code, English
name and useful lookups:

`CountryCode` · `CurrencyCode` · `LanguageCode` (alpha-2) · `LanguageAlpha3Code` · `LocaleCode` ·
`ScriptCode`

These back the `COUNTRY_SELECT`, `LOCALE_SELECT` and `TIME_ZONE_SELECT` form fields.

## License

Apache License 2.0.
