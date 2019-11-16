package org.bardframework.commons.util;

import org.springframework.util.Assert;

/**
 * Created by vahid (va.zafari@gmail.com) on 10/30/17.
 */
public final class AssertionUtils extends Assert {

    private AssertionUtils() {
    }

    public static void ge(long greater, long smaller, String message) {
        if (greater < smaller) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void ge(long greater, long smaller) {
        ge(greater, smaller, "[Assertion failed] - first argument '" + greater + "' must greater than or equal with second argument '" + smaller + "'");
    }

    public static void gt(long greater, long smaller, String message) {
        if (greater <= smaller) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void gt(long greater, long smaller) {
        gt(greater, smaller, "[Assertion failed] - first argument '" + greater + "' must greater than second argument '" + smaller + "'");
    }

    public static void assertEquals(byte[] file1, byte[] file2) {
        if (null == file1 ^ null == file2) {
            throw new IllegalArgumentException("files aren't match");
        }
        //TODO complete assertion
    }
}
