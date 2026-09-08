common-apm
==========

[![Maven Central](https://img.shields.io/badge/maven--central-5.3.1-blue.svg)](https://repo1.maven.org/maven2/org/bardframework/commons/common-apm/)
[![License](http://img.shields.io/:license-apache-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

Attaches the Elastic APM agent to the running JVM programmatically — no `-javaagent` flag, no changes to how the application is started.

Part of [**Bard Commons**](../README.md) · [Bard Framework](https://github.com/bardframework)

```xml
<dependency>
    <groupId>org.bardframework.commons</groupId>
    <artifactId>common-apm</artifactId>
    <version>5.3.1</version>
</dependency>
```

## Why

The usual way to run Elastic APM is `java -javaagent:elastic-apm-agent.jar -Delastic.apm.…`. That
means the agent jar must be shipped next to the application and every launcher — Dockerfile, systemd
unit, IDE run configuration, CI job — must be kept in sync. This module moves the decision into the
application's own configuration.

## Usage

```java
@Configuration
@Import(ElasticApmConfiguration.class)
public class AppConfiguration { }
```

```properties
bard.apm.elastic.enabled=true
bard.apm.elastic.elastic.server_url=http://apm-server:8200
bard.apm.elastic.elastic.service_name=order-service
bard.apm.elastic.elastic.environment=production
bard.apm.elastic.elastic.application_packages=com.example
```

Everything under `bard.apm.elastic.elastic.*` is passed straight to the agent, so the full Elastic APM
configuration reference applies unchanged.

Attachment happens in a `@PostConstruct` using `PROGRAMMATIC_SELF_ATTACH`, and is skipped entirely
when `bard.apm.elastic.enabled` is not `true` — an environment without an APM server simply does not
attach.

## Choosing between this and OpenTelemetry

For new services prefer [`common-opentelemetry`](../common-opentelemetry): it is vendor-neutral, and
Elastic APM ingests OTLP alongside Jaeger, Tempo and the rest. Reach for this module when you are
joining an existing Elastic APM deployment that relies on the agent's own instrumentation.

## License

Apache License 2.0.
