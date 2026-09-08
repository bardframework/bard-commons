package org.bardframework.commons.utils;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class DigestAndEncodingUtilsTest {

    private static final String SAMPLE = "bard framework";

    @Test
    void base64_roundTrips() {
        String encoded = EncodingUtils.encodeBase64(SAMPLE.getBytes(StandardCharsets.UTF_8));
        assertThat(new String(EncodingUtils.decodeBase64(encoded), StandardCharsets.UTF_8)).isEqualTo(SAMPLE);
    }

    @Test
    void base64_ofEmptyInputIsEmpty() {
        assertThat(EncodingUtils.encodeBase64(new byte[0])).isEmpty();
    }

    @Test
    void hexEncode_producesTwoCharactersPerByte() {
        assertThat(EncodingUtils.hexEncode(new byte[]{0x00, 0x0F, (byte) 0xFF})).isEqualToIgnoringCase("000fff");
    }

    @Test
    void sha_isDeterministic() throws Exception {
        assertThat(DigestUtils.sha(SAMPLE)).isEqualTo(DigestUtils.sha(SAMPLE));
    }

    @Test
    void sha_differsForDifferentInput() throws Exception {
        assertThat(DigestUtils.sha(SAMPLE)).isNotEqualTo(DigestUtils.sha(SAMPLE + "!"));
    }

    @Test
    void sha512_isLongerThanSha() throws Exception {
        assertThat(DigestUtils.sha512(SAMPLE).length()).isGreaterThan(DigestUtils.sha(SAMPLE).length());
    }

    @Test
    void urlEncode_escapesReservedCharacters() {
        assertThat(EncodingUtils.urlEncode("a b&c")).doesNotContain(" ").contains("%26");
    }
}
