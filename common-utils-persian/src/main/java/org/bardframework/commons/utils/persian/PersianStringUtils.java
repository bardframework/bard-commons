package org.bardframework.commons.utils.persian;

/**
 * Created by Vahid Zafari on 8/12/2016.
 */
public final class PersianStringUtils {

    private PersianStringUtils() {
    }

    /**
     * @return remove some characters form the given string and return it
     */
    public static String getComparableString(String string) {
        return (string == null ? null : string
                .replace('ي', 'ی')
                .replace('ئ', 'ی')
                .replace('ك', 'ک')
                .replace('ؤ', 'و')
                .replace('>', Character.MIN_VALUE)
                .replace('<', Character.MIN_VALUE)
                .replace(' ', Character.MIN_VALUE)
                .replace(')', Character.MIN_VALUE)
                .replace('(', Character.MIN_VALUE)
                .replace('.', Character.MIN_VALUE)
                .replace(',', Character.MIN_VALUE));
    }

    /**
     * @return remove some characters form the given string and return it
     */
    public static String disinfectPersianText(String dirtyString) {
        if (null == dirtyString) {
            return null;
        }
        dirtyString = dirtyString
                .replace('ي', 'ی')
                .replace('ك', 'ک')
                .replace('ؤ', 'و');
        while (dirtyString.contains("  ")) {
            dirtyString = dirtyString.replaceAll(" {2}", " ");
        }
        return dirtyString.trim();
    }
}
