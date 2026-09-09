common-captcha
==============

[![Maven Central](https://img.shields.io/badge/maven--central-5.6.1-blue.svg)](https://repo1.maven.org/maven2/org/bardframework/commons/common-captcha/)
[![License](http://img.shields.io/:license-apache-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

Image captcha generation with Latin, Persian and Arabic alphabets.

Part of [**Bard Commons**](../README.md) · [Bard Framework](https://github.com/bardframework)

```xml
<dependency>
    <groupId>org.bardframework.commons</groupId>
    <artifactId>common-captcha</artifactId>
    <version>5.6.1</version>
</dependency>
```

## Usage

```java
CaptchaService service = new CaptchaService();

GeneratedCaptcha captcha = service.generateCaptcha(CaptchaType.PERSIAN_NUMBER_CHAR, 5);
captcha.getValue();   // the expected answer — keep it server-side
captcha.getImage();   // PNG bytes to send to the client
```

Store the answer where the client cannot reach it and compare on submit. **Never** send the answer to
the client in any form.

## Types

| `CaptchaType` | Character set |
| --- | --- |
| `ENGLISH_CHAR` | `A-Z a-z` |
| `ENGLISH_NUMBER` | `0-9` |
| `ENGLISH_NUMBER_CHAR` | letters and digits |
| `PERSIAN_CHAR` | Persian letters |
| `PERSIAN_NUMBER_CHAR` | Persian letters and digits |
| `PERSIAN_NUMBER_TEXT` | digits rendered with Persian shaping |
| `ARABIC_CHAR` | Arabic letters |
| `ARABIC_NUMBER_CHAR` | Arabic letters and digits |

Each type carries a `CaptchaTypeInfo` — its character set, its font, and whether it is right-to-left.
Override `captchaTypeInfoMap` to change fonts or restrict the alphabet (dropping visually ambiguous
characters is a common, worthwhile tweak).

Persian and Arabic glyph shaping is handled by `LetterConverterUtility` from
[`common-utils-persian`](../common-utils-persian) — letters are joined correctly rather than rendered
in isolated forms.

## In a flow

You rarely call this directly. `CaptchaFieldTemplate` in
[`bard-flow`](https://github.com/bardframework/bard-form-parent) wraps it into a form field that
generates, delivers, validates and invalidates the captcha for you — including an audio variant for
accessibility, and validation ordered before every other field so a bot never reaches the expensive
checks.

## License

Apache License 2.0.
