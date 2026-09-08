package org.bardframework.commons.captcha;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CaptchaServiceTest {

    private final CaptchaService service = new CaptchaService();

    @ParameterizedTest
    @EnumSource(CaptchaType.class)
    @DisplayName("every type produces an answer and a decodable PNG")
    void generateCaptcha_producesAnswerAndImage(CaptchaType type) throws Exception {
        GeneratedCaptcha captcha = service.generateCaptcha(type, 5);

        assertThat(captcha.getValue()).isNotBlank();
        assertThat(captcha.getImage()).isNotEmpty();

        BufferedImage image = ImageIO.read(new ByteArrayInputStream(captcha.getImage()));
        assertThat(image).isNotNull();
        assertThat(image.getWidth()).isPositive();
        assertThat(image.getHeight()).isPositive();
    }

    @ParameterizedTest
    @EnumSource(CaptchaType.class)
    @DisplayName("the answer has exactly the requested number of characters")
    void generateCaptcha_respectsRequestedLength(CaptchaType type) {
        assertThat(service.generateCaptcha(type, 6).getValue()).hasSize(6);
    }

    @ParameterizedTest
    @EnumSource(value = CaptchaType.class, names = {"ENGLISH_NUMBER", "PERSIAN_NUMBER_TEXT"})
    void generateCaptcha_numericTypesProduceDigitsOnly(CaptchaType type) {
        assertThat(service.generateCaptcha(type, 5).getValue()).containsOnlyDigits();
    }

    @Test
    @DisplayName("the answer is drawn from the configured alphabet of the type")
    void generateCaptcha_usesAlphabetOfType() {
        String alphabet = service.getCaptchaTypeInfoMap().get(CaptchaType.ENGLISH_CHAR).getChars();
        String value = service.generateCaptcha(CaptchaType.ENGLISH_CHAR, 8).getValue();
        assertThat(value.chars().allMatch(c -> alphabet.indexOf(c) >= 0)).isTrue();
    }

    @Test
    @DisplayName("successive captchas differ — a constant answer would defeat the purpose")
    void generateCaptcha_isNotConstant() {
        Set<String> values = new HashSet<>();
        for (int i = 0; i < 20; i++) {
            values.add(service.generateCaptcha(CaptchaType.ENGLISH_NUMBER_CHAR, 6).getValue());
        }
        assertThat(values).hasSizeGreaterThan(1);
    }

    @Test
    void generateCaptcha_failsForTypeWithoutConfiguration() {
        CaptchaService bare = new CaptchaService();
        bare.getCaptchaTypeInfoMap().remove(CaptchaType.ENGLISH_CHAR);
        assertThatThrownBy(() -> bare.generateCaptcha(CaptchaType.ENGLISH_CHAR, 5))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("no chars specify");
    }
}
