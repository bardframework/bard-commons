package org.bardframework.commons.util;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Locale;

/**
 * Created by vahid (va.zafari@gmail.com) on 10/30/17.
 */
public final class StringUtils extends org.springframework.util.StringUtils {

    /**
     * @param num
     * @param digits
     * @return get a number and return that in string format, digit depict the
     * result string length if the given number is less than digit counts, this
     * method add 0 to the first of result string
     */
    public static String getString(long num, int digits) {
        if (digits < 1) {
            return Constants.EMPTY_STRING;
        }
        boolean minus = false;
        if (num < 0) {
            minus = true;
            num *= -1;
        }
        int diff = digits - String.valueOf(num).length();
        if (diff == 0) {
            return String.valueOf(num);
        }
        if (diff > 0) {
            StringBuilder zero = new StringBuilder();
            while (zero.length() < diff) {
                zero.append('0');
            }
            zero.append(num);
            return zero.toString();
        }
        return (minus ? "-" : Constants.EMPTY_STRING) + Double.valueOf(num / Math.pow(10.00, -1.00 * diff));
    }

    /**
     * Check whether the given {@code String} not contains actual <em>text</em>.
     * <p>More specifically, this method returns {@code true} if the
     * {@code String} is  {@code null} or its length is 0 or it not contains at least one non-whitespace character.
     *
     * @param str the {@code String} to check (may be {@code null})
     * @return {@code true} if the {@code String} is  {@code null}, its
     * length is  0, or it contain whitespace only
     * @see #hasText(CharSequence)
     */
    public static boolean hasNotText(String str) {
        return !hasText(str);
    }

    /**
     * remove all non-digit character (not minus in start if string '-') form
     * string
     *
     * @param num
     * @return
     */
    public static String removeNonDigitCharacters(String num) {
        if (num == null) {
            return num;
        }
        num = num.trim();
        if (num.isEmpty()) {
            return num;
        }
        boolean minus = num.charAt(0) == '-';
        for (Character c : num.toCharArray()) {
            if (!Character.isDigit(c)) {
                num = num.replace(String.valueOf(c), Constants.EMPTY_STRING);
            }
        }
        return minus ? "-" : Constants.EMPTY_STRING + num;
    }

    /**
     * @param num
     * @return remove digits from the given string
     * @throws
     */
    public static String removeDigit(String num) {
        if (num != null) {
            for (Character c : num.toCharArray()) {
                if (Character.isDigit(c)) {
                    num = num.replace(String.valueOf(c), Constants.EMPTY_STRING);
                }
            }
        }
        return num;
    }

    /**
     * @param num
     * @return if given parameter contains string return true else return false
     * @throws
     */
    public static boolean containDigit(String num) {
        if (num != null) {
            for (Character c : num.toCharArray()) {
                if (Character.isDigit(c)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isEmpty(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isEmpty(cs);
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isNotEmpty(cs);
    }

    public static boolean isAnyEmpty(CharSequence... css) {
        return org.apache.commons.lang3.StringUtils.isAnyEmpty(css);
    }

    public static boolean isNoneEmpty(CharSequence... css) {
        return org.apache.commons.lang3.StringUtils.isNoneEmpty(css);
    }

    public static boolean isBlank(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isBlank(cs);
    }

    public static boolean isNotBlank(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isNotBlank(cs);
    }

    public static boolean isAnyBlank(CharSequence... css) {
        return org.apache.commons.lang3.StringUtils.isAnyBlank(css);
    }

    public static boolean isNoneBlank(CharSequence... css) {
        return org.apache.commons.lang3.StringUtils.isNoneBlank(css);
    }

    public static String trim(String str) {
        return org.apache.commons.lang3.StringUtils.trim(str);
    }

    public static String trimToNull(String str) {
        return org.apache.commons.lang3.StringUtils.trimToNull(str);
    }

    public static String trimToEmpty(String str) {
        return org.apache.commons.lang3.StringUtils.trimToEmpty(str);
    }

    public static String truncate(String str, int maxWidth) {
        return org.apache.commons.lang3.StringUtils.truncate(str, maxWidth);
    }

    public static String truncate(String str, int offset, int maxWidth) {
        return org.apache.commons.lang3.StringUtils.truncate(str, offset, maxWidth);
    }

    public static String strip(String str) {
        return org.apache.commons.lang3.StringUtils.strip(str);
    }

    public static String stripToNull(String str) {
        return org.apache.commons.lang3.StringUtils.stripToNull(str);
    }

    public static String stripToEmpty(String str) {
        return org.apache.commons.lang3.StringUtils.stripToEmpty(str);
    }

    public static String strip(String str, String stripChars) {
        return org.apache.commons.lang3.StringUtils.strip(str, stripChars);
    }

    public static String stripStart(String str, String stripChars) {
        return org.apache.commons.lang3.StringUtils.stripStart(str, stripChars);
    }

    public static String stripEnd(String str, String stripChars) {
        return org.apache.commons.lang3.StringUtils.stripEnd(str, stripChars);
    }

    public static String[] stripAll(String... strs) {
        return org.apache.commons.lang3.StringUtils.stripAll(strs);
    }

    public static String[] stripAll(String[] strs, String stripChars) {
        return org.apache.commons.lang3.StringUtils.stripAll(strs, stripChars);
    }

    public static String stripAccents(String input) {
        return org.apache.commons.lang3.StringUtils.stripAccents(input);
    }

    public static boolean equals(CharSequence cs1, CharSequence cs2) {
        return org.apache.commons.lang3.StringUtils.equals(cs1, cs2);
    }

    public static boolean equalsIgnoreCase(CharSequence str1, CharSequence str2) {
        return org.apache.commons.lang3.StringUtils.equalsIgnoreCase(str1, str2);
    }

    public static int compare(String str1, String str2) {
        return org.apache.commons.lang3.StringUtils.compare(str1, str2);
    }

    public static int compare(String str1, String str2, boolean nullIsLess) {
        return org.apache.commons.lang3.StringUtils.compare(str1, str2, nullIsLess);
    }

    public static int compareIgnoreCase(String str1, String str2) {
        return org.apache.commons.lang3.StringUtils.compareIgnoreCase(str1, str2);
    }

    public static int compareIgnoreCase(String str1, String str2, boolean nullIsLess) {
        return org.apache.commons.lang3.StringUtils.compareIgnoreCase(str1, str2, nullIsLess);
    }

    public static boolean equalsAny(CharSequence string, CharSequence... searchStrings) {
        return org.apache.commons.lang3.StringUtils.equalsAny(string, searchStrings);
    }

    public static boolean equalsAnyIgnoreCase(CharSequence string, CharSequence... searchStrings) {
        return org.apache.commons.lang3.StringUtils.equalsAnyIgnoreCase(string, searchStrings);
    }

    public static int indexOf(CharSequence seq, int searchChar) {
        return org.apache.commons.lang3.StringUtils.indexOf(seq, searchChar);
    }

    public static int indexOf(CharSequence seq, int searchChar, int startPos) {
        return org.apache.commons.lang3.StringUtils.indexOf(seq, searchChar, startPos);
    }

    public static int indexOf(CharSequence seq, CharSequence searchSeq) {
        return org.apache.commons.lang3.StringUtils.indexOf(seq, searchSeq);
    }

    public static int indexOf(CharSequence seq, CharSequence searchSeq, int startPos) {
        return org.apache.commons.lang3.StringUtils.indexOf(seq, searchSeq, startPos);
    }

    public static int ordinalIndexOf(CharSequence str, CharSequence searchStr, int ordinal) {
        return org.apache.commons.lang3.StringUtils.ordinalIndexOf(str, searchStr, ordinal);
    }

    public static int indexOfIgnoreCase(CharSequence str, CharSequence searchStr) {
        return org.apache.commons.lang3.StringUtils.indexOfIgnoreCase(str, searchStr);
    }

    public static int indexOfIgnoreCase(CharSequence str, CharSequence searchStr, int startPos) {
        return org.apache.commons.lang3.StringUtils.indexOfIgnoreCase(str, searchStr, startPos);
    }

    public static int lastIndexOf(CharSequence seq, int searchChar) {
        return org.apache.commons.lang3.StringUtils.lastIndexOf(seq, searchChar);
    }

    public static int lastIndexOf(CharSequence seq, int searchChar, int startPos) {
        return org.apache.commons.lang3.StringUtils.lastIndexOf(seq, searchChar, startPos);
    }

    public static int lastIndexOf(CharSequence seq, CharSequence searchSeq) {
        return org.apache.commons.lang3.StringUtils.lastIndexOf(seq, searchSeq);
    }

    public static int lastOrdinalIndexOf(CharSequence str, CharSequence searchStr, int ordinal) {
        return org.apache.commons.lang3.StringUtils.lastOrdinalIndexOf(str, searchStr, ordinal);
    }

    public static int lastIndexOf(CharSequence seq, CharSequence searchSeq, int startPos) {
        return org.apache.commons.lang3.StringUtils.lastIndexOf(seq, searchSeq, startPos);
    }

    public static int lastIndexOfIgnoreCase(CharSequence str, CharSequence searchStr) {
        return org.apache.commons.lang3.StringUtils.lastIndexOfIgnoreCase(str, searchStr);
    }

    public static int lastIndexOfIgnoreCase(CharSequence str, CharSequence searchStr, int startPos) {
        return org.apache.commons.lang3.StringUtils.lastIndexOfIgnoreCase(str, searchStr, startPos);
    }

    public static boolean contains(CharSequence seq, int searchChar) {
        return org.apache.commons.lang3.StringUtils.contains(seq, searchChar);
    }

    public static boolean contains(CharSequence seq, CharSequence searchSeq) {
        return org.apache.commons.lang3.StringUtils.contains(seq, searchSeq);
    }

    public static boolean containsIgnoreCase(CharSequence str, CharSequence searchStr) {
        return org.apache.commons.lang3.StringUtils.containsIgnoreCase(str, searchStr);
    }

    public static int indexOfAny(CharSequence cs, char... searchChars) {
        return org.apache.commons.lang3.StringUtils.indexOfAny(cs, searchChars);
    }

    public static int indexOfAny(CharSequence cs, String searchChars) {
        return org.apache.commons.lang3.StringUtils.indexOfAny(cs, searchChars);
    }

    public static boolean containsAny(CharSequence cs, char... searchChars) {
        return org.apache.commons.lang3.StringUtils.containsAny(cs, searchChars);
    }

    public static boolean containsAny(CharSequence cs, CharSequence searchChars) {
        return org.apache.commons.lang3.StringUtils.containsAny(cs, searchChars);
    }

    public static boolean containsAny(CharSequence cs, CharSequence... searchCharSequences) {
        return org.apache.commons.lang3.StringUtils.containsAny(cs, searchCharSequences);
    }

    public static int indexOfAnyBut(CharSequence cs, char... searchChars) {
        return org.apache.commons.lang3.StringUtils.indexOfAnyBut(cs, searchChars);
    }

    public static int indexOfAnyBut(CharSequence seq, CharSequence searchChars) {
        return org.apache.commons.lang3.StringUtils.indexOfAnyBut(seq, searchChars);
    }

    public static boolean containsOnly(CharSequence cs, char... valid) {
        return org.apache.commons.lang3.StringUtils.containsOnly(cs, valid);
    }

    public static boolean containsOnly(CharSequence cs, String validChars) {
        return org.apache.commons.lang3.StringUtils.containsOnly(cs, validChars);
    }

    public static boolean containsNone(CharSequence cs, char... searchChars) {
        return org.apache.commons.lang3.StringUtils.containsNone(cs, searchChars);
    }

    public static boolean containsNone(CharSequence cs, String invalidChars) {
        return org.apache.commons.lang3.StringUtils.containsNone(cs, invalidChars);
    }

    public static int indexOfAny(CharSequence str, CharSequence... searchStrs) {
        return org.apache.commons.lang3.StringUtils.indexOfAny(str, searchStrs);
    }

    public static int lastIndexOfAny(CharSequence str, CharSequence... searchStrs) {
        return org.apache.commons.lang3.StringUtils.lastIndexOfAny(str, searchStrs);
    }

    public static String substring(String str, int start) {
        return org.apache.commons.lang3.StringUtils.substring(str, start);
    }

    public static String substring(String str, int start, int end) {
        return org.apache.commons.lang3.StringUtils.substring(str, start, end);
    }

    public static String left(String str, int len) {
        return org.apache.commons.lang3.StringUtils.left(str, len);
    }

    public static String right(String str, int len) {
        return org.apache.commons.lang3.StringUtils.right(str, len);
    }

    public static String mid(String str, int pos, int len) {
        return org.apache.commons.lang3.StringUtils.mid(str, pos, len);
    }

    public static String substringBefore(String str, String separator) {
        return org.apache.commons.lang3.StringUtils.substringBefore(str, separator);
    }

    public static String substringAfter(String str, String separator) {
        return org.apache.commons.lang3.StringUtils.substringAfter(str, separator);
    }

    public static String substringBeforeLast(String str, String separator) {
        return org.apache.commons.lang3.StringUtils.substringBeforeLast(str, separator);
    }

    public static String substringAfterLast(String str, String separator) {
        return org.apache.commons.lang3.StringUtils.substringAfterLast(str, separator);
    }

    public static String substringBetween(String str, String tag) {
        return org.apache.commons.lang3.StringUtils.substringBetween(str, tag);
    }

    public static String substringBetween(String str, String open, String close) {
        return org.apache.commons.lang3.StringUtils.substringBetween(str, open, close);
    }

    public static String[] substringsBetween(String str, String open, String close) {
        return org.apache.commons.lang3.StringUtils.substringsBetween(str, open, close);
    }

    public static String[] split(String str) {
        return org.apache.commons.lang3.StringUtils.split(str);
    }

    public static String[] split(String str, char separatorChar) {
        return org.apache.commons.lang3.StringUtils.split(str, separatorChar);
    }

    public static String[] split(String str, String separatorChars, int max) {
        return org.apache.commons.lang3.StringUtils.split(str, separatorChars, max);
    }

    public static String[] splitByWholeSeparator(String str, String separator) {
        return org.apache.commons.lang3.StringUtils.splitByWholeSeparator(str, separator);
    }

    public static String[] splitByWholeSeparator(String str, String separator, int max) {
        return org.apache.commons.lang3.StringUtils.splitByWholeSeparator(str, separator, max);
    }

    public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator) {
        return org.apache.commons.lang3.StringUtils.splitByWholeSeparatorPreserveAllTokens(str, separator);
    }

    public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator, int max) {
        return org.apache.commons.lang3.StringUtils.splitByWholeSeparatorPreserveAllTokens(str, separator, max);
    }

    public static String[] splitPreserveAllTokens(String str) {
        return org.apache.commons.lang3.StringUtils.splitPreserveAllTokens(str);
    }

    public static String[] splitPreserveAllTokens(String str, char separatorChar) {
        return org.apache.commons.lang3.StringUtils.splitPreserveAllTokens(str, separatorChar);
    }

    public static String[] splitPreserveAllTokens(String str, String separatorChars) {
        return org.apache.commons.lang3.StringUtils.splitPreserveAllTokens(str, separatorChars);
    }

    public static String[] splitPreserveAllTokens(String str, String separatorChars, int max) {
        return org.apache.commons.lang3.StringUtils.splitPreserveAllTokens(str, separatorChars, max);
    }

    public static String[] splitByCharacterType(String str) {
        return org.apache.commons.lang3.StringUtils.splitByCharacterType(str);
    }

    public static String[] splitByCharacterTypeCamelCase(String str) {
        return org.apache.commons.lang3.StringUtils.splitByCharacterTypeCamelCase(str);
    }

    public static <T> String join(T... elements) {
        return org.apache.commons.lang3.StringUtils.join(elements);
    }

    public static String join(Object[] array, char separator) {
        return org.apache.commons.lang3.StringUtils.join(array, separator);
    }

    public static String join(long[] array, char separator) {
        return org.apache.commons.lang3.StringUtils.join(array, separator);
    }

    public static String join(int[] array, char separator) {
        return org.apache.commons.lang3.StringUtils.join(array, separator);
    }

    public static String join(short[] array, char separator) {
        return org.apache.commons.lang3.StringUtils.join(array, separator);
    }

    public static String join(byte[] array, char separator) {
        return org.apache.commons.lang3.StringUtils.join(array, separator);
    }

    public static String join(char[] array, char separator) {
        return org.apache.commons.lang3.StringUtils.join(array, separator);
    }

    public static String join(float[] array, char separator) {
        return org.apache.commons.lang3.StringUtils.join(array, separator);
    }

    public static String join(double[] array, char separator) {
        return org.apache.commons.lang3.StringUtils.join(array, separator);
    }

    public static String join(Object[] array, char separator, int startIndex, int endIndex) {
        return org.apache.commons.lang3.StringUtils.join(array, separator, startIndex, endIndex);
    }

    public static String join(long[] array, char separator, int startIndex, int endIndex) {
        return org.apache.commons.lang3.StringUtils.join(array, separator, startIndex, endIndex);
    }

    public static String join(int[] array, char separator, int startIndex, int endIndex) {
        return org.apache.commons.lang3.StringUtils.join(array, separator, startIndex, endIndex);
    }

    public static String join(byte[] array, char separator, int startIndex, int endIndex) {
        return org.apache.commons.lang3.StringUtils.join(array, separator, startIndex, endIndex);
    }

    public static String join(short[] array, char separator, int startIndex, int endIndex) {
        return org.apache.commons.lang3.StringUtils.join(array, separator, startIndex, endIndex);
    }

    public static String join(char[] array, char separator, int startIndex, int endIndex) {
        return org.apache.commons.lang3.StringUtils.join(array, separator, startIndex, endIndex);
    }

    public static String join(double[] array, char separator, int startIndex, int endIndex) {
        return org.apache.commons.lang3.StringUtils.join(array, separator, startIndex, endIndex);
    }

    public static String join(float[] array, char separator, int startIndex, int endIndex) {
        return org.apache.commons.lang3.StringUtils.join(array, separator, startIndex, endIndex);
    }

    public static String join(Object[] array, String separator) {
        return org.apache.commons.lang3.StringUtils.join(array, separator);
    }

    public static String join(Object[] array, String separator, int startIndex, int endIndex) {
        return org.apache.commons.lang3.StringUtils.join(array, separator, startIndex, endIndex);
    }

    public static String join(Iterator<?> iterator, char separator) {
        return org.apache.commons.lang3.StringUtils.join(iterator, separator);
    }

    public static String join(Iterator<?> iterator, String separator) {
        return org.apache.commons.lang3.StringUtils.join(iterator, separator);
    }

    public static String join(Iterable<?> iterable, char separator) {
        return org.apache.commons.lang3.StringUtils.join(iterable, separator);
    }

    public static String join(Iterable<?> iterable, String separator) {
        return org.apache.commons.lang3.StringUtils.join(iterable, separator);
    }

    public static String joinWith(String separator, Object... objects) {
        return org.apache.commons.lang3.StringUtils.joinWith(separator, objects);
    }

    public static String deleteWhitespace(String str) {
        return org.apache.commons.lang3.StringUtils.deleteWhitespace(str);
    }

    public static String removeStart(String str, String remove) {
        return org.apache.commons.lang3.StringUtils.removeStart(str, remove);
    }

    public static String removeStartIgnoreCase(String str, String remove) {
        return org.apache.commons.lang3.StringUtils.removeStartIgnoreCase(str, remove);
    }

    public static String removeEnd(String str, String remove) {
        return org.apache.commons.lang3.StringUtils.removeEnd(str, remove);
    }

    public static String removeEndIgnoreCase(String str, String remove) {
        return org.apache.commons.lang3.StringUtils.removeEndIgnoreCase(str, remove);
    }

    public static String remove(String str, String remove) {
        return org.apache.commons.lang3.StringUtils.remove(str, remove);
    }

    public static String removeIgnoreCase(String str, String remove) {
        return org.apache.commons.lang3.StringUtils.removeIgnoreCase(str, remove);
    }

    public static String remove(String str, char remove) {
        return org.apache.commons.lang3.StringUtils.remove(str, remove);
    }

    public static String replaceOnce(String text, String searchString, String replacement) {
        return org.apache.commons.lang3.StringUtils.replaceOnce(text, searchString, replacement);
    }

    public static String replaceOnceIgnoreCase(String text, String searchString, String replacement) {
        return org.apache.commons.lang3.StringUtils.replaceOnceIgnoreCase(text, searchString, replacement);
    }

    public static String replaceIgnoreCase(String text, String searchString, String replacement) {
        return org.apache.commons.lang3.StringUtils.replaceIgnoreCase(text, searchString, replacement);
    }

    public static String replace(String text, String searchString, String replacement, int max) {
        return org.apache.commons.lang3.StringUtils.replace(text, searchString, replacement, max);
    }

    public static String replaceIgnoreCase(String text, String searchString, String replacement, int max) {
        return org.apache.commons.lang3.StringUtils.replaceIgnoreCase(text, searchString, replacement, max);
    }

    public static String replaceEach(String text, String[] searchList, String[] replacementList) {
        return org.apache.commons.lang3.StringUtils.replaceEach(text, searchList, replacementList);
    }

    public static String replaceEachRepeatedly(String text, String[] searchList, String[] replacementList) {
        return org.apache.commons.lang3.StringUtils.replaceEachRepeatedly(text, searchList, replacementList);
    }

    public static String replaceChars(String str, char searchChar, char replaceChar) {
        return org.apache.commons.lang3.StringUtils.replaceChars(str, searchChar, replaceChar);
    }

    public static String replaceChars(String str, String searchChars, String replaceChars) {
        return org.apache.commons.lang3.StringUtils.replaceChars(str, searchChars, replaceChars);
    }

    public static String overlay(String str, String overlay, int start, int end) {
        return org.apache.commons.lang3.StringUtils.overlay(str, overlay, start, end);
    }

    public static String chomp(String str) {
        return org.apache.commons.lang3.StringUtils.chomp(str);
    }

    public static String chop(String str) {
        return org.apache.commons.lang3.StringUtils.chop(str);
    }

    public static String repeat(String str, int repeat) {
        return org.apache.commons.lang3.StringUtils.repeat(str, repeat);
    }

    public static String repeat(String str, String separator, int repeat) {
        return org.apache.commons.lang3.StringUtils.repeat(str, separator, repeat);
    }

    public static String repeat(char ch, int repeat) {
        return org.apache.commons.lang3.StringUtils.repeat(ch, repeat);
    }

    public static String rightPad(String str, int size) {
        return org.apache.commons.lang3.StringUtils.rightPad(str, size);
    }

    public static String rightPad(String str, int size, char padChar) {
        return org.apache.commons.lang3.StringUtils.rightPad(str, size, padChar);
    }

    public static String rightPad(String str, int size, String padStr) {
        return org.apache.commons.lang3.StringUtils.rightPad(str, size, padStr);
    }

    public static String leftPad(String str, int size) {
        return org.apache.commons.lang3.StringUtils.leftPad(str, size);
    }

    public static String leftPad(String str, int size, char padChar) {
        return org.apache.commons.lang3.StringUtils.leftPad(str, size, padChar);
    }

    public static String leftPad(String str, int size, String padStr) {
        return org.apache.commons.lang3.StringUtils.leftPad(str, size, padStr);
    }

    public static int length(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.length(cs);
    }

    public static String center(String str, int size) {
        return org.apache.commons.lang3.StringUtils.center(str, size);
    }

    public static String center(String str, int size, char padChar) {
        return org.apache.commons.lang3.StringUtils.center(str, size, padChar);
    }

    public static String center(String str, int size, String padStr) {
        return org.apache.commons.lang3.StringUtils.center(str, size, padStr);
    }

    public static String upperCase(String str) {
        return org.apache.commons.lang3.StringUtils.upperCase(str);
    }

    public static String upperCase(String str, Locale locale) {
        return org.apache.commons.lang3.StringUtils.upperCase(str, locale);
    }

    public static String lowerCase(String str) {
        return org.apache.commons.lang3.StringUtils.lowerCase(str);
    }

    public static String lowerCase(String str, Locale locale) {
        return org.apache.commons.lang3.StringUtils.lowerCase(str, locale);
    }

    public static String swapCase(String str) {
        return org.apache.commons.lang3.StringUtils.swapCase(str);
    }

    public static int countMatches(CharSequence str, CharSequence sub) {
        return org.apache.commons.lang3.StringUtils.countMatches(str, sub);
    }

    public static int countMatches(CharSequence str, char ch) {
        return org.apache.commons.lang3.StringUtils.countMatches(str, ch);
    }

    public static boolean isAlpha(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isAlpha(cs);
    }

    public static boolean isAlphaSpace(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isAlphaSpace(cs);
    }

    public static boolean isAlphanumeric(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isAlphanumeric(cs);
    }

    public static boolean isAlphanumericSpace(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isAlphanumericSpace(cs);
    }

    public static boolean isAsciiPrintable(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isAsciiPrintable(cs);
    }

    public static boolean isNumeric(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isNumeric(cs);
    }

    public static boolean isNumericSpace(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isNumericSpace(cs);
    }

    public static boolean isWhitespace(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isWhitespace(cs);
    }

    public static boolean isAllLowerCase(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isAllLowerCase(cs);
    }

    public static boolean isAllUpperCase(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isAllUpperCase(cs);
    }

    public static String defaultString(String str) {
        return org.apache.commons.lang3.StringUtils.defaultString(str);
    }

    public static String defaultString(String str, String defaultStr) {
        return org.apache.commons.lang3.StringUtils.defaultString(str, defaultStr);
    }

    public static <T extends CharSequence> T defaultIfBlank(T str, T defaultStr) {
        return org.apache.commons.lang3.StringUtils.defaultIfBlank(str, defaultStr);
    }

    public static <T extends CharSequence> T defaultIfEmpty(T str, T defaultStr) {
        return org.apache.commons.lang3.StringUtils.defaultIfEmpty(str, defaultStr);
    }

    public static String rotate(String str, int shift) {
        return org.apache.commons.lang3.StringUtils.rotate(str, shift);
    }

    public static String reverse(String str) {
        return org.apache.commons.lang3.StringUtils.reverse(str);
    }

    public static String reverseDelimited(String str, char separatorChar) {
        return org.apache.commons.lang3.StringUtils.reverseDelimited(str, separatorChar);
    }

    public static String abbreviate(String str, int maxWidth) {
        return org.apache.commons.lang3.StringUtils.abbreviate(str, maxWidth);
    }

    public static String abbreviate(String str, int offset, int maxWidth) {
        return org.apache.commons.lang3.StringUtils.abbreviate(str, offset, maxWidth);
    }

    public static String abbreviateMiddle(String str, String middle, int length) {
        return org.apache.commons.lang3.StringUtils.abbreviateMiddle(str, middle, length);
    }

    public static String difference(String str1, String str2) {
        return org.apache.commons.lang3.StringUtils.difference(str1, str2);
    }

    public static int indexOfDifference(CharSequence cs1, CharSequence cs2) {
        return org.apache.commons.lang3.StringUtils.indexOfDifference(cs1, cs2);
    }

    public static int indexOfDifference(CharSequence... css) {
        return org.apache.commons.lang3.StringUtils.indexOfDifference(css);
    }

    public static String getCommonPrefix(String... strs) {
        return org.apache.commons.lang3.StringUtils.getCommonPrefix(strs);
    }

    public static boolean startsWith(CharSequence str, CharSequence prefix) {
        return org.apache.commons.lang3.StringUtils.startsWith(str, prefix);
    }

    public static boolean startsWithIgnoreCase(CharSequence str, CharSequence prefix) {
        return org.apache.commons.lang3.StringUtils.startsWithIgnoreCase(str, prefix);
    }

    public static boolean startsWithAny(CharSequence sequence, CharSequence... searchStrings) {
        return org.apache.commons.lang3.StringUtils.startsWithAny(sequence, searchStrings);
    }

    public static boolean endsWith(CharSequence str, CharSequence suffix) {
        return org.apache.commons.lang3.StringUtils.endsWith(str, suffix);
    }

    public static boolean endsWithIgnoreCase(CharSequence str, CharSequence suffix) {
        return org.apache.commons.lang3.StringUtils.endsWithIgnoreCase(str, suffix);
    }

    public static String normalizeSpace(String str) {
        return org.apache.commons.lang3.StringUtils.normalizeSpace(str);
    }

    public static boolean endsWithAny(CharSequence sequence, CharSequence... searchStrings) {
        return org.apache.commons.lang3.StringUtils.endsWithAny(sequence, searchStrings);
    }

    public static String appendIfMissing(String str, CharSequence suffix, CharSequence... suffixes) {
        return org.apache.commons.lang3.StringUtils.appendIfMissing(str, suffix, suffixes);
    }

    public static String appendIfMissingIgnoreCase(String str, CharSequence suffix, CharSequence... suffixes) {
        return org.apache.commons.lang3.StringUtils.appendIfMissingIgnoreCase(str, suffix, suffixes);
    }

    public static String prependIfMissing(String str, CharSequence prefix, CharSequence... prefixes) {
        return org.apache.commons.lang3.StringUtils.prependIfMissing(str, prefix, prefixes);
    }

    public static String prependIfMissingIgnoreCase(String str, CharSequence prefix, CharSequence... prefixes) {
        return org.apache.commons.lang3.StringUtils.prependIfMissingIgnoreCase(str, prefix, prefixes);
    }

    public static String toEncodedString(byte[] bytes, Charset charset) {
        return org.apache.commons.lang3.StringUtils.toEncodedString(bytes, charset);
    }

    public static String wrap(String str, char wrapWith) {
        return org.apache.commons.lang3.StringUtils.wrap(str, wrapWith);
    }

    public static String wrap(String str, String wrapWith) {
        return org.apache.commons.lang3.StringUtils.wrap(str, wrapWith);
    }

    public static String wrapIfMissing(String str, char wrapWith) {
        return org.apache.commons.lang3.StringUtils.wrapIfMissing(str, wrapWith);
    }

    public static String wrapIfMissing(String str, String wrapWith) {
        return org.apache.commons.lang3.StringUtils.wrapIfMissing(str, wrapWith);
    }
}
