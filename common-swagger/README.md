common-swagger
==============

[![Maven Central](https://img.shields.io/badge/maven--central-5.3.1-blue.svg)](https://repo1.maven.org/maven2/org/bardframework/commons/common-swagger/)
[![License](http://img.shields.io/:license-apache-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

springdoc-openapi wired up in one property — and denied by default, so production is closed unless you open it.

Part of [**Bard Commons**](../README.md) · [Bard Framework](https://github.com/bardframework)

```xml
<dependency>
    <groupId>org.bardframework.commons</groupId>
    <artifactId>common-swagger</artifactId>
    <version>5.3.1</version>
</dependency>
```

## Usage

```java
@Configuration
@Import(SwaggerConfiguration.class)
public class AppConfiguration { }
```

```properties
bard.web.swagger.enabled=true
```

Swagger UI is then at `/swagger-ui/index.html`.

## What you get

* A `public-api` group covering `/**`.
* An `OpenAPI` bean with an HTTP bearer security scheme already declared, so the *Authorize* button
  works without further setup.
* An `Info` bean with sensible defaults.
* A `SecurityFilterChain` for `/swagger-ui/**` and `/v3/api-docs/**` that is **`denyAll()`** unless
  `bard.web.swagger.enabled=true`.

That last point is the reason this module exists. Leaving API documentation reachable in production
hands an attacker a map of your surface; here it takes an explicit property to expose it, and
forgetting the property fails closed.

## Overriding

Every bean is `@ConditionalOnMissingBean`, so define your own and it wins:

```java
@Bean
public Info info() {
    return new Info().title("Orders API").version("2.1")
                     .description("…")
                     .contact(new Contact().name("Platform").email("platform@example.com"));
}

@Bean
public GroupedOpenApi adminApi() {
    return GroupedOpenApi.builder().group("admin").pathsToMatch("/api/admin/**").build();
}
```

## License

Apache License 2.0.
