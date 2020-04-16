package org.bardframework.commons.reflection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ReflectionUtilsTest {

    @Test
    void getSupersOf() {
        Assertions.assertThat(ReflectionUtils.getSupersOf(null, true, true, true)).isEmpty();
        Assertions.assertThat(ReflectionUtils.getSupersOf(Object.class, true, true, true)).isEmpty();
        Assertions.assertThat(ReflectionUtils.getSupersOf(FileFindHandlerAdapter.class, false, true, false)).isEmpty();
        Assertions.assertThat(ReflectionUtils.getSupersOf(FileFindHandlerAdapter.class, true, true, true)).hasSize(2);
        Assertions.assertThat(ReflectionUtils.getSupersOf(FileFindHandlerAdapter.class, false, true, true)).hasSize(1);
    }
}