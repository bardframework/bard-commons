common-opentelemetry
====================

[![Maven Central](https://img.shields.io/badge/maven--central-6.1.1-blue.svg)](https://repo1.maven.org/maven2/org/bardframework/commons/common-opentelemetry/)
[![License](http://img.shields.io/:license-apache-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

OpenTelemetry tracing, JVM metrics under OTel conventions, and a filter that ties log lines and HTTP responses to the current trace.

Part of [**Bard Commons**](../README.md) · [Bard Framework](https://github.com/bardframework)

```xml
<dependency>
    <groupId>org.bardframework.commons</groupId>
    <artifactId>common-opentelemetry</artifactId>
    <version>5.6.1</version>
</dependency>
```

## Usage

```java
@Configuration
@Import(OpenTelemetryConfiguration.class)
public class AppConfiguration { }
```

Configure the exporter with the standard Spring Boot / OpenTelemetry properties
(`management.otlp.tracing.endpoint`, `management.tracing.sampling.probability`, …).

## What it sets up

* **Tracing** through Micrometer Tracing with the OpenTelemetry bridge, including the
  `OpenTelemetryServerRequestObservationConvention` for HTTP server spans.
* **JVM and system metrics** — class loading, memory, threads, processor, uptime — published under
  OpenTelemetry semantic conventions rather than Micrometer's native names, so dashboards match what
  other OTel-instrumented services emit.
* **`OpenTelemetryAppender` for Logback** — application logs are shipped as OTel log records with
  trace context attached.
* **`ContextPropagatingTaskDecorator`** — trace context survives a hop onto an executor, so
  `@Async` work stays inside the trace.
* **`TraceIdFilter`** — adds `X-Trace-Id` to every response.

That last one is small and disproportionately useful: a user reports a failure, quotes the trace id
from the response, and you jump straight to the trace instead of guessing from timestamps.

## Related

* [`common-monitoring`](../common-monitoring) — Prometheus metrics endpoint.
* [`common-apm`](../common-apm) — Elastic APM, if that is your backend instead.

## License

Apache License 2.0.
