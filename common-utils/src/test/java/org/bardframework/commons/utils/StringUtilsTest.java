package org.bardframework.commons.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class StringUtilsTest {

    @Test
    void hideString() {
        char hidder = '*';
        String result = StringUtils.hideString(null, 4, '*');
        Assertions.assertThat(result).isNull();
        result = StringUtils.hideString("", 4, hidder);
        Assertions.assertThat(result).isEmpty();
        result = StringUtils.hideString("AB", 4, hidder);
        Assertions.assertThat(result).isEqualTo("**");
        result = StringUtils.hideString("asvadasdasd", 4, hidder);
        Assertions.assertThat(StringUtils.countMatches(result, hidder)).isEqualTo(4);
        result = StringUtils.hideString("+98912*45*55", 4, hidder);
        Assertions.assertThat(StringUtils.countMatches(result, hidder)).isEqualTo(4);
    }
}