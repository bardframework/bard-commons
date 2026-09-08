package org.bardframework.commons.utils.persian;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class IranianNationalNoUtilsTest {

    @ParameterizedTest
    @ValueSource(longs = {614038480L, 1739962435L, 3520005972L, 12345679L, 9987654320L})
    @DisplayName("numbers whose check digit matches the algorithm are accepted")
    void checkNationalNo_acceptsValidNumbers(long nationalNo) {
        assertThat(IranianNationalNoUtils.checkNationalNo(nationalNo)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(longs = {614038481L, 1739962436L, 3520005973L, 9987654321L})
    @DisplayName("a wrong check digit is rejected")
    void checkNationalNo_rejectsWrongCheckDigit(long nationalNo) {
        assertThat(IranianNationalNoUtils.checkNationalNo(nationalNo)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(longs = {1111111111L, 2222222222L, 3333333333L, 9999999999L, 123456789L})
    @DisplayName("black-listed numbers are rejected even when the check digit is consistent")
    void checkNationalNo_rejectsBlackListedNumbers(long nationalNo) {
        assertThat(IranianNationalNoUtils.checkNationalNo(nationalNo)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, 1L, 9999999L})
    @DisplayName("numbers below the minimum length are rejected")
    void checkNationalNo_rejectsTooShortNumbers(long nationalNo) {
        assertThat(IranianNationalNoUtils.checkNationalNo(nationalNo)).isFalse();
    }

    @Test
    @DisplayName("leading zeros are significant — the value is padded to ten digits")
    void checkNationalNo_padsToTenDigits() {
        assertThat(IranianNationalNoUtils.checkNationalNo(12345679L)).isTrue();
    }
}
