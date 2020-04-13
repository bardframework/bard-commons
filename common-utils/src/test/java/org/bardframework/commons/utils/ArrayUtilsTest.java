package org.bardframework.commons.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class ArrayUtilsTest {

    @Test
    void getNumberArray() {
        String[] array = ArrayUtils.getNumberArray(RandomUtils.nextInt(1, 20), RandomUtils.nextInt(20, 30), RandomUtils.nextInt(1, 5));
        Assertions.assertThat(array.length).isNotZero();
    }

    @Test
    void getInvalidNumberArray() {
        String[] array = ArrayUtils.getNumberArray(RandomUtils.nextInt(1, 20), RandomUtils.nextInt(20, 30), -1);
        Assertions.assertThat(array.length).isZero();
    }
}
