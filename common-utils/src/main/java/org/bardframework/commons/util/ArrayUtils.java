package org.bardframework.commons.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by Vahid Zafari on 8/12/2016.
 */
public final class ArrayUtils {

    private ArrayUtils() {
    }

    /**
     * @param start, int end, int increment
     * @return Array of numbers from start to end with specific increment step
     * if increment < 0 then returns String object with zero length
     * @throws
     */
    public static String[] getNumberArray(int start, int end, int increment) {
        if (increment > 0) {
            int count = (end - start) / increment;
            String[] numbers = new String[count];
            for (int i = 0; i < count; i++) {
                numbers[i] = String.valueOf(start + (i * increment));
            }
            return numbers;
        } else {
            return new String[0];
        }
    }

    /**
     * @param original(Array), int splitSize
     * @return split given array to small array with split size length
     * @throws
     */
    public static <T> List<T[]> arraySplit(T[] original, int splitSize) {
        List<T[]> result = new ArrayList<>();
        if (original == null) {
            return result;
        }
        int currentPosition = 0;
        while (currentPosition < original.length) {
            int remaining = original.length - currentPosition + 1;
            if (remaining <= splitSize) {
                result.add(Arrays.copyOfRange(original, currentPosition, original.length));
            } else {
                result.add(Arrays.copyOfRange(original, currentPosition, currentPosition + splitSize));
            }
            currentPosition += splitSize;
        }
        return result;
    }

    /**
     * @param nums
     * @return List of numbers in Long format
     */
    public static List<Long> getLongList(Collection<String> nums) {
        List<Long> ids = new ArrayList<>();
        if (nums != null) {
            for (String num : nums) {
                ids.add(Long.valueOf(num));
            }
        }
        return ids;
    }

    /**
     * @param nums
     * @return List of numbers in Long format
     */
    public static List<Long> getLongList(String... nums) {
        return getLongList(Arrays.asList(nums));
    }
}
