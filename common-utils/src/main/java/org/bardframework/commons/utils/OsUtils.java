package org.bardframework.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by Vahid Zafari on 8/12/2016.
 */
public final class OsUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(OsUtils.class);
    private static final MBeanServer M_BEAN_SERVER = ManagementFactory.getPlatformMBeanServer();

    private OsUtils() {
        /*
            prevent instantiation
         */
    }

    public static int getCpuUsagePercent() {
        ObjectName name;
        try {
            name = ObjectName.getInstance("java.lang:type=OperatingSystem");
            AttributeList list = M_BEAN_SERVER.getAttributes(name, new String[]{"ProcessCpuLoad"});
            if (list.isEmpty()) {
                return 0;
            }
            Attribute att = (Attribute) list.get(0);
            Double value = (Double) att.getValue();
            // usually takes a couple of seconds before we get real values
            if (value == -1.0) {
                return 0;
            }
            // returns a percentage value with 1 decimal point precision
            return ((int) (value * 1000) / 10);
        } catch (Exception e) {
            LOGGER.error("");
            return 0;
        }
    }

    /**
     * @param start, int end, int increment
     * @return Array of numbers from start to end with specific increment step
     * if increment less than 0 then returns String object with zero length
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
     * @param original, int splitSize
     * @return split given collection to small collection with split size length
     */
    public static <T> List<List<T>> collectionSplit(List original, int splitSize) {
        List<List<T>> result = new ArrayList<>();
        if (original == null) {
            return result;
        }
        int currentPosition = 0;
        while (currentPosition < original.size()) {
            int remaining = original.size() - currentPosition + 1;
            if (remaining <= splitSize) {
                result.add(new ArrayList(List.of(Arrays.copyOfRange(original.toArray(), currentPosition, original.size()))));
            } else {
                result.add(new ArrayList(List.of(Arrays.copyOfRange(original.toArray(), currentPosition, currentPosition + splitSize))));
            }
            currentPosition += splitSize;
        }
        return result;
    }

    /**
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
     * @return List of numbers in Long format
     */
    public static List<Long> getLongList(String... nums) {
        return getLongList(List.of(nums));
    }
}
