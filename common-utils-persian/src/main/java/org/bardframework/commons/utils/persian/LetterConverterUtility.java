package org.bardframework.commons.utils.persian;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public final class LetterConverterUtility {

    private static final String ENGLISH_NUM = "0123456789";
    private static final String PERSIAN_NUM = "۰۱۲۳۴۵۶۷۸۹";
    private static final String ARABIC_NUM = "٠١٢٣٤٥٦٧٨٩";
    private static final String ARABIC_CHR = "كئيىؤ";
    private static final String PERSIAN_CHR = "کیییو";
    private static final String PERSIAN_ALL = PERSIAN_CHR + ENGLISH_NUM;
    private static final String ARABIC_ALL = ARABIC_CHR + ARABIC_NUM;
    private static final String[] digit1 = {"صفر", "یک", "دو", "سه", "چهار", "پنج", "شش", "هفت", "هشت", "نه"};
    private static final String[] digit1_5 = {"یازده", "دوازده", "سیزده", "چهارده", "پانزده", "شانزده", "هفده", "هجده", "نوزده"};
    private static final String[] digit2 = {"ده", "بیست", "سی", "چهل", "پنجاه", "شصت", "هفتاد", "هشتاد", "نود"};
    private static final String[] digit3 = {"یکصد", "دویست", "سیصد", "چهارصد", "پانصد", "ششصد", "هفتصد", "هشتصد", "نهصد"};
    private static final String[] steps = {"هزار", "میلیون", "میلیارد", "تریلیون", "کادریلیون", "کوینتریلیون", "سکستریلیون", "سپتریلیون", "اکتریلیون", "نونیلیون", "دسیلیون"};

    private LetterConverterUtility() {
    }

    /**
     * @return new string equivalent to <code>arabicString</code> convert arabic letter to persian and numeric to english
     */
    public static String convertArabicCharacters(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        for (int j = 0; j < str.length(); j++) {
            char c = str.charAt(j);
            int i = ARABIC_ALL.indexOf(c);
            if (i != -1) {
                str = str.replace(c, PERSIAN_ALL.charAt(i));
            }
        }
        return str;
    }

    public static String convertFarsiNumbersToEnglish(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        for (int j = 0; j < str.length(); j++) {
            char c = str.charAt(j);
            int i = PERSIAN_NUM.indexOf(c);
            if (i != -1) {
                str = str.replace(c, ENGLISH_NUM.charAt(i));
            }
        }

        return str;
    }

    public static String convertEnglishNumbersToFarsi(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        for (int j = 0; j < str.length(); j++) {
            char c = str.charAt(j);
            int i = ENGLISH_NUM.indexOf(c);
            if (i != -1) {
                str = str.replace(c, PERSIAN_NUM.charAt(i));
            }
        }

        return str;
    }

    private static String commaSeparatedInternal(String amount) {
        StringBuilder sb = new StringBuilder();
        int len = amount.length();
        for (int i = 0; i < len; i++) {
            sb.append(amount.charAt(len - 1 - i));
            if ((i + 1) % 3 == 0 && i < len - 1) {
                sb.append(',');
            }
        }
        return sb.reverse().toString();
    }

    public static String commaSeparated(String amount) {
        return convertEnglishNumbersToFarsi(commaSeparatedInternal(amount));
    }

    private static List<String> groupToWords(int group) {
        int d3 = (int) Math.floor(group / 100.00);
        int d2 = (int) Math.floor((group - d3 * 100) / 10.00);
        int d1 = group - d3 * 100 - d2 * 10;

        List<String> groupArray = new ArrayList<>();

        if (d3 != 0) {
            groupArray.add(digit3[d3 - 1]);
        }

        if (d2 == 1 && d1 != 0) { // 11-19
            groupArray.add(digit1_5[d1 - 1]);
        } else if (d2 != 0 && d1 == 0) { // 10-20-...-90
            groupArray.add(digit2[d2 - 1]);
        } else if (d2 == 0 && d1 == 0) {
            /*
            00
             */
        } else if (d2 == 0) { // 1-9
            groupArray.add(digit1[d1]);
        } else { // Others
            groupArray.add(digit2[d2 - 1]);
            groupArray.add(digit1[d1]);
        }

        return !groupArray.isEmpty() ? groupArray : null;
    }

    public static String convertDigitToFarsiLetter(long number) {
        List<String> parts = LetterConverterUtility.convertDigitToFarsiLetterParts(Math.abs(number), " ");
        return String.join(" ", parts);
    }

    public static String convertDigitToFarsiLetterRial(long number) {
        boolean isNegative = false;
        if (number < 0) {
            number *= -1;
            isNegative = true;
        }
        List<String> parts = LetterConverterUtility.convertDigitToFarsiLetterParts(number, " و ");
        return (isNegative ? "منفی " : "") + String.join(" و ", parts) + " ریال";
    }

    public static List<String> convertDigitToFarsiLetterParts(long number, String delimiter) {
        String formated = commaSeparatedInternal(String.valueOf(number));
        String[] groups = formated.split(",");

        int len = groups.length;

        List<String> parts = new ArrayList<>();
        for (int step = 0; step < len; step++) {
            int group = Integer.parseInt(groups[step]);
            List<String> groupWords = groupToWords(group);
            if (groupWords != null) {
                StringBuilder part = new StringBuilder();
                part.append(String.join(delimiter, groupWords));
                if (len - step - 1 > 0) {
                    part.append(' ');
                    part.append(steps[len - step - 2]);
                }
                parts.add(String.valueOf(part));
            }
        }
        return parts;
    }
}