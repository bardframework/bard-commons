package org.bardframework.commons.util;

import java.util.Random;

/**
 * Created by Vahid Zafari on 8/12/2016.
 */
public final class RandomUtils {

    private static final Random RANDOM = new Random();

    private RandomUtils() {
    }

    /**
     * @param min
     * @param max
     * @return first integer number that upper than @param min and less or <b>equal</b> than @param max
     */
    public static int getRandomNumberInRange(int min, int max) {
        return RANDOM.ints(min, (max + 1)).findFirst().getAsInt();
    }

    /**
     * @param length, the length of generated string, if less than 1 then return empty string
     * @return random string
     */
    public static String randomString(int length) {
        StringBuilder builder = new StringBuilder();
        while (length > 0) {
            builder.append((char) RANDOM.nextInt(2000));
            length--;
        }
        return builder.toString();
    }

    public static int nextInt() {
        return RANDOM.nextInt();
    }

    public static int nextInt(int bound) {
        return RANDOM.nextInt(bound);
    }
}
