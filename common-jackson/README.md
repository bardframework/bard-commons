common-jackson
==============

[![Maven Central](https://img.shields.io/badge/maven--central-6.1.1-blue.svg)](https://repo1.maven.org/maven2/org/bardframework/commons/common-jackson/)
[![License](http://img.shields.io/:license-apache-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

Reusable Jackson serializers and deserializers, plus a bridge that turns any Spring `Converter` into a Jackson one.

Part of [**Bard Commons**](../README.md) · [Bard Framework](https://github.com/bardframework)

```xml
<dependency>
    <groupId>org.bardframework.commons</groupId>
    <artifactId>common-jackson</artifactId>
    <version>6.1.1</version>
</dependency>
```

## Jackson 3

This module targets **Jackson 3** (`tools.jackson.core:jackson-databind`). If you are coming from
Jackson 2, the differences that matter here are:

| Jackson 2 | Jackson 3 |
| --- | --- |
| `com.fasterxml.jackson.core` / `.databind` | `tools.jackson.core` / `tools.jackson.databind` |
| `com.fasterxml.jackson.annotation.*` | unchanged — annotations keep their package |
| `JsonSerializer` / `JsonDeserializer` | `ValueSerializer` / `ValueDeserializer` |
| `SerializerProvider` | `SerializationContext` |
| `JsonProcessingException`, `IOException` | `JacksonException` — unchecked |
| `JsonGenerator.writeObject(...)` | `writePOJO(...)` |
| `parser.getCurrentValue()` | `parser.currentValue()` |
| `TextNode` | `StringNode` |
| `jackson-datatype-jsr310` | built into `jackson-databind` |
| `new ObjectMapper().configure(...)` | `JsonMapper.builder()....build()` — mappers are immutable |

Spring Boot 4 manages both generations, so a project can migrate one module at a time.

## The bridge

Rather than writing a `JsonSerializer` per type, write a Spring `Converter` — which you can also use
elsewhere — and wrap it:

```java
public class MoneyToStringConverter implements Converter<Money, String> { ... }

public class MoneySerializer extends ConverterSerializer<Money, String> {
    public MoneySerializer() { super(new MoneyToStringConverter()); }
}
```

`ConverterSerializer` and `ConverterDeserializer` are the two wrappers.

## Ready-made converters

**Dates** — pick the wire representation you want without touching the domain type:

`LocalDateToLongConverter` · `LongToLocalDateConverter` · `LocalDateTimeToLongConverter` ·
`LongToLocalDateTimeConverter` · `LocalDateToStringConverter` · `LocalDateTimeToStringConverter`

**Lenient scalars** — accept a JSON string where a number or boolean is expected, which mobile and
form clients frequently send:

`StringToByteConverter` · `StringToShortConverter` · `StringToIntegerConverter` ·
`StringToLongConverter` · `StringToDoubleConverter` · `StringToBooleanConverter`

**Binary** — `StringToByteArrayConverter`, `ByteArrayToStringConverter` (Base64 on the wire, `byte[]`
in Java).

**Enums** — `StringToEnumSerializer`.

**Sanitising** — `JsonRloRemoverHtmlScapeDeserializer` neutralises Unicode bidi-override characters
(`U+202E`) on the way in, so stored text cannot render as something other than what it is.

> Despite its name this deserializer does **not** HTML-escape. If you need escaping as well, layer it
> in your own deserializer or escape at render time.

```java
public class ArticleDto {
    @JsonDeserialize(using = JsonRloRemoverHtmlScapeDeserializer.class)
    private String title;

    @JsonSerialize(using = LocalDateToLongConverter.class)
    private LocalDate publishedAt;
}
```

## License

Apache License 2.0.
