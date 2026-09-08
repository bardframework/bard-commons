package org.bardframework.commons.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class StringTemplateUtilsTest {

    @Test
    @DisplayName("default delimiters are '::name::'")
    void fillTemplate_replacesPlaceholders() {
        assertThat(StringTemplateUtils.fillTemplate("Hello ::name::!", Map.of("name", "Ali")))
                .isEqualTo("Hello Ali!");
    }

    @Test
    void fillTemplate_replacesMultipleAndRepeatedPlaceholders() {
        assertThat(StringTemplateUtils.fillTemplate("::a::-::b::-::a::", Map.of("a", "1", "b", "2")))
                .isEqualTo("1-2-1");
    }

    @Test
    @DisplayName("an unknown placeholder is left untouched rather than blanked")
    void fillTemplate_keepsUnknownPlaceholder() {
        assertThat(StringTemplateUtils.fillTemplate("Hello ::unknown::", Map.of("name", "Ali")))
                .isEqualTo("Hello ::unknown::");
    }

    @Test
    void fillTemplate_supportsCustomDelimiters() {
        assertThat(StringTemplateUtils.fillTemplate("Hello ${name}", "${", "}", Map.of("name", "Ali")))
                .isEqualTo("Hello Ali");
    }

    @Test
    void fillTemplate_convertsNonStringValues() {
        assertThat(StringTemplateUtils.fillTemplate("count: ::n::", Map.of("n", 42)))
                .isEqualTo("count: 42");
    }

    @Test
    void fillTemplate_returnsBlankInputUnchanged() {
        assertThat(StringTemplateUtils.fillTemplate(null, Map.of())).isNull();
        assertThat(StringTemplateUtils.fillTemplate("", Map.of())).isEmpty();
        assertThat(StringTemplateUtils.fillTemplate("   ", Map.of())).isEqualTo("   ");
    }

    @Test
    void fillTemplate_toleratesNullValueInArgs() {
        Map<String, Object> args = new HashMap<>();
        args.put("name", null);
        assertThat(StringTemplateUtils.fillTemplate("Hello ::name::", args)).isEqualTo("Hello ::name::");
    }

    @Test
    void fillTemplate_leavesTextWithoutPlaceholdersUnchanged() {
        assertThat(StringTemplateUtils.fillTemplate("plain text", Map.of("a", "b"))).isEqualTo("plain text");
    }
}
