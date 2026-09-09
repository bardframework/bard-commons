common-monitoring
=================

[![Maven Central](https://img.shields.io/badge/maven--central-6.1.1-blue.svg)](https://repo1.maven.org/maven2/org/bardframework/commons/common-monitoring/)
[![License](http://img.shields.io/:license-apache-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

Actuator and Prometheus metrics, with `/actuator/**` denied unless explicitly enabled.

Part of [**Bard Commons**](../README.md) · [Bard Framework](https://github.com/bardframework)

```xml
<dependency>
    <groupId>org.bardframework.commons</groupId>
    <artifactId>common-monitoring</artifactId>
    <version>5.6.1</version>
</dependency>
```

## Usage

```java
@Configuration
@Import(MonitoringConfiguration.class)
public class AppConfiguration { }
```

```properties
bard.web.monitoring.enabled=true
management.endpoints.web.exposure.include=health,info,metrics,prometheus
```

Metrics are then scrapeable at `/actuator/prometheus`.

## Behaviour

The module brings in `spring-boot-starter-actuator` and `micrometer-registry-prometheus`, and
registers a `SecurityFilterChain` matched to `GET /actuator/**` that:

* `permitAll()` when `bard.web.monitoring.enabled=true`;
* **`denyAll()`** otherwise.

Actuator exposes heap dumps, environment properties and thread dumps. Defaulting to `denyAll` means
an unconfigured environment is a closed one — the failure mode is a missing dashboard, not a leaked
environment.

In a real deployment, keep the property `true` and restrict reachability at the network layer
(a separate management port, or an ingress rule that only your Prometheus can traverse).

## Related

* [`common-opentelemetry`](../common-opentelemetry) — tracing and log correlation.
* [`common-apm`](../common-apm) — Elastic APM agent.

## License

Apache License 2.0.
