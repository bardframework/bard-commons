package org.bardframework.commons.utils.persian;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class PersianStringUtilsTest {

    @ParameterizedTest
    @CsvSource({
            "'كتاب', 'کتاب'",
            "'يک', 'یک'",
            "'مسئول', 'مسیول'",
            "'مؤسسه', 'موسسه'"
    })
    @DisplayName("Arabic look-alikes are normalised to their Persian form")
    void getComparableString_normalisesArabicCharacters(String input, String expected) {
        assertThat(PersianStringUtils.getComparableString(input)).isEqualTo(expected);
    }

    @Test
    @DisplayName("separators are stripped so two spellings of the same name compare equal")
    void getComparableString_stripsSeparators() {
        assertThat(PersianStringUtils.getComparableString("علی رضایی"))
                .isEqualTo(PersianStringUtils.getComparableString("علی.رضایی"))
                .isEqualTo(PersianStringUtils.getComparableString("علی,رضایی"));
    }

    @Test
    void getComparableString_returnsNullForNull() {
        assertThat(PersianStringUtils.getComparableString(null)).isNull();
    }

    @Test
    void disinfectPersianText_normalisesCharacters() {
        assertThat(PersianStringUtils.disinfectPersianText("كيف")).isEqualTo("کیف");
    }

    @Test
    @DisplayName("repeated spaces collapse and the result is trimmed")
    void disinfectPersianText_collapsesWhitespace() {
        assertThat(PersianStringUtils.disinfectPersianText("  سلام     دنیا  ")).isEqualTo("سلام دنیا");
    }

    @Test
    void disinfectPersianText_returnsNullForNull() {
        assertThat(PersianStringUtils.disinfectPersianText(null)).isNull();
    }

    @Test
    @DisplayName("unlike getComparableString, disinfect keeps single spaces")
    void disinfectPersianText_keepsSingleSpaces() {
        assertThat(PersianStringUtils.disinfectPersianText("علی رضایی")).isEqualTo("علی رضایی");
    }
}
