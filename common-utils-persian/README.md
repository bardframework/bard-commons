common-utils-persian
====================

[![Maven Central](https://img.shields.io/badge/maven--central-5.3.1-blue.svg)](https://repo1.maven.org/maven2/org/bardframework/commons/common-utils-persian/)
[![License](http://img.shields.io/:license-apache-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

Persian and Iranian specifics: text normalisation, national-ID validation, and Jackson deserializers that clean input at the boundary.

Part of [**Bard Commons**](../README.md) · [Bard Framework](https://github.com/bardframework)

```xml
<dependency>
    <groupId>org.bardframework.commons</groupId>
    <artifactId>common-utils-persian</artifactId>
    <version>5.3.1</version>
</dependency>
```

Depends on [`common-utils`](../common-utils) and
[`jalali-date`](https://github.com/bardframework/jalali-date).

## Why normalise text at all

Persian text arriving from a browser is rarely clean. Users type Arabic `ي` and `ك` where Persian
expects `ی` and `ک`; they paste Arabic-Indic digits; copied text carries invisible RLO/LRO
direction-override characters that can make a stored string *display* as something other than what it
is. If you normalise in the service layer you will forget somewhere. Normalise during
deserialisation and it is impossible to forget.

## Text utilities

```java
PersianStringUtils.normalize("كتاب ي");   // كتاب ي  →  کتاب ی
PersianStringUtils.removeRlo(text);       // strip bidi control characters
```

| Class | Purpose |
| --- | --- |
| `PersianStringUtils` | Arabic→Persian letters, digit normalisation, bidi-control removal. |
| `IranianNationalNoUtils` | Validate an Iranian national ID by its check digit; generate valid samples for tests. |
| `LetterConverterUtility` | Shape and join Persian glyphs for rendering into images — used by [`common-captcha`](../common-captcha). |
| `UtilityMethods` | Number-to-Persian-words and related conversions. |

## Jackson converters

Register these on a DTO field, or globally on the `ObjectMapper`, and every value is cleaned as it is
read:

| Converter | Effect |
| --- | --- |
| `ArabicToPersianDeserializer` | Arabic characters → Persian on input. |
| `ArabicToPersianCharacterSerializer` | The same on output. |
| `NormalPersianCharacterDeserializer` | Full Persian character normalisation. |
| `NormalPersianNumberDeserializer` | Arabic-Indic / Persian digits → ASCII digits. |
| `PersianStringDisinfectant` | Combined normalisation plus control-character stripping. |

```java
public class CustomerDto {
    @JsonDeserialize(using = PersianStringDisinfectant.class)
    private String firstName;
}
```

## License

Apache License 2.0.
