package org.bardframework.commons.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vahid Zafari on 8/12/2016.
 */
public final class NumberUtils {

    private NumberUtils() {
    }

    /**
     * @return count of last digit from number
     */
    public static Long getLastDigit(long number, int countOfLastDigit) {
        int length = String.valueOf(number).length();
        if (length <= countOfLastDigit) {
            return number;
        } else {
            return Long.valueOf(String.valueOf(number).substring(length - countOfLastDigit, length));
        }
    }

    /**
     * remove all non-digit character (not minus in start if string '-') form
     * string, if remain string not empty, return long value of that, else
     * return 0
     */
    public static Long getNumeric(String num) {
        try {
            num = num.trim();
            boolean minus = num.charAt(0) == '-';
            for (char c : num.toCharArray()) {
                if (!Character.isDigit(c)) {
                    num = num.replace(String.valueOf(c), StringUtils.EMPTY);
                }
            }
            return Long.valueOf((minus ? "-" : StringUtils.EMPTY) + num);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException | NullPointerException | StringIndexOutOfBoundsException ex) {
            return 0L;
        }
    }

    /**
     * @return remove all non-digit character form string, if remain string not
     * empty, return long value of that, else return 0
     */
    public static Long getNumeric(Object num) {
        if (num != null) {
            return getNumeric(num.toString());
        }
        return 0L;
    }

    /**
     * @return Numeric list of given string collection
     */
    public static Set<Long> getNumeric(Collection<String> collection) {
        Set<Long> temp = new HashSet<>();
        if (collection != null) {
            for (String num : collection) {
                temp.add(getNumeric(num));
            }
        }
        return temp;
    }

    /**
     * remove all non-digit character form string, if remain string not empty,
     * return double value of that, else return 0
     */
    public static double getDouble(Object object) {
        if (object == null) {
            return 0;
        }
        String num = object.toString();
        for (char c : num.toCharArray()) {
            if (!Character.isDigit(c)) {
                num = num.replace(String.valueOf(c), StringUtils.EMPTY);
            }
        }
        if (!num.isEmpty()) {
            try {
                return Double.parseDouble(num);
            } catch (NumberFormatException ex) {
                return 0;
            }
        }
        return 0;
    }

    /**
     * @return list of numbers in given string
     */
    public static Set<Long> getNumbers(String string) {
        Set<Long> numbers = new HashSet<>();
        if (string != null && !string.isEmpty()) {
            int i = 0;
            StringBuilder number;
            while (i < string.length()) {
                number = new StringBuilder();
                while (i < string.length() && Character.isDigit(string.charAt(i++))) {
                    number.append(string.charAt(i - 1));
                }
                if (number.length() > 0) {
                    numbers.add((string.charAt(i - number.length() - 2) == '-' ? -1 : 1) * Long.parseLong(number.toString()));
                }
            }
        }
        return numbers;
    }

    /**
     * <p>Convert a {@code String} to a {@code long}, returning a default value if the conversion fails.</p>
     *
     * <p>If the string is {@code null}, the default value is returned.</p>
     *
     * <pre>
     *   NumberUtils.toLong(null, 1L) = 1L
     *   NumberUtils.toLong("", 1L)   = 1L
     *   NumberUtils.toLong("1", 0L)  = 1L
     * </pre>
     *
     * @param str          the string to convert, may be null
     * @param defaultValue the default value
     * @return the long represented by the string, or the default if conversion fails
     */
    public static long toLong(final String str, final long defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(str);
        } catch (final NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * <p>Convert a {@code String} to a {@code int}, returning a default value if the conversion fails.</p>
     *
     * <p>If the string is {@code null}, the default value is returned.</p>
     *
     * <pre>
     *   NumberUtils.toInt(null, 1) = 1
     *   NumberUtils.toInt("", 1)   = 1
     *   NumberUtils.toInt("1", 0)  = 1
     * </pre>
     *
     * @param str          the string to convert, may be null
     * @param defaultValue the default value
     * @return the int represented by the string, or the default if conversion fails
     */
    public static int toInt(final String str, final int defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str);
        } catch (final NumberFormatException nfe) {
            return defaultValue;
        }
    }
}
