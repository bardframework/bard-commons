common-sms
==========

[![Maven Central](https://img.shields.io/badge/maven--central-5.3.1-blue.svg)](https://repo1.maven.org/maven2/org/bardframework/commons/common-sms/)
[![License](http://img.shields.io/:license-apache-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

An `SmsSender` abstraction and a template-driven HTTP implementation that fits most gateways without writing code.

Part of [**Bard Commons**](../README.md) · [Bard Framework](https://github.com/bardframework)

```xml
<dependency>
    <groupId>org.bardframework.commons</groupId>
    <artifactId>common-sms</artifactId>
    <version>5.3.1</version>
</dependency>
```

## The contract

```java
public interface SmsSender {
    boolean send(Map<String, Object> args) throws IOException;
    default boolean send(String to, String message, Map<String, Object> args) throws IOException;
}
```

`args` carries `to` and `message` plus anything else your gateway or template needs.

## HTTP gateways without code

Most SMS providers are a GET or POST with credentials and text in the URL or body, and a response you
pattern-match for success. `SmsSenderHttpCall` makes that configuration:

```xml
<bean id="smsSender" class="org.bardframework.commons.sms.SmsSenderHttpCall"
      c:httpMethod="GET"
      c:urlTemplate="https://gateway.example.com/send?user=${user}&amp;pass=${pass}&amp;to=${to}&amp;text=${message}"
      c:successPattern="^\d{10,}$"/>
```

The URL, headers and body are templates filled from `args`; `successPattern` is a regex matched
against the response body. Phone numbers are masked in logs.

## Composing senders

| Implementation | Behaviour |
| --- | --- |
| `SmsSenderHttpCall` | Calls a configured HTTP endpoint. |
| `SmsSenderRanked` | Holds several senders and delegates to the highest-ranked under an overridable `comparator()` — subclass it to rank by cost, quota or health. It picks one sender per call; it does not retry the others. |
| `SmsSenderNoOp` | Logs a warning and reports success. The right default in development. |

## In a flow

`SotpFieldTemplate` and `MessageSenderSms` in
[`bard-flow`](https://github.com/bardframework/bard-form-parent) send through an `SmsSender`, so a
one-time-password step is configuration rather than code.

## License

Apache License 2.0.
