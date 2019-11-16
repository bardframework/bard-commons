package org.bardframework.commons.util;

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
     * @param number
     * @param countOfLastDigit
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
     *
     * @param num
     * @return
     */
    public static Long getNumeric(String num) {
        try {
            num = num.trim();
            boolean minus = num.charAt(0) == '-';
            for (char c : num.toCharArray()) {
                if (!Character.isDigit(c)) {
                    num = num.replace(String.valueOf(c), Constants.EMPTY_STRING);
                }
            }
            return Long.valueOf((minus ? "-" : Constants.EMPTY_STRING) + num);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException | NullPointerException | StringIndexOutOfBoundsException ex) {
            return 0L;
        }
    }

    /**
     * @param num
     * @return remove all non-digit character form string, if remain string not
     * empty, return long value of that, else return 0
     * @throws
     */
    public static Long getNumeric(Object num) {
        if (num != null) {
            return getNumeric(num.toString());
        }
        return 0L;
    }

    /**
     * @param collection
     * @return Numeric list of given string collection
     * @throws
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
     *
     * @param object
     * @return
     */
    public static double getDouble(Object object) {
        if (object == null) {
            return 0;
        }
        String num = object.toString();
        for (char c : num.toCharArray()) {
            if (!Character.isDigit(c)) {
                num = num.replace(String.valueOf(c), Constants.EMPTY_STRING);
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
     * @param string
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
}
