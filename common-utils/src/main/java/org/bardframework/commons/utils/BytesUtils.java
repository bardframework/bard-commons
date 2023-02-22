package org.bardframework.commons.utils;

import lombok.experimental.UtilityClass;

import java.util.Arrays;

/**
 * Created by Vahid Zafari.
 */
@UtilityClass
public class BytesUtils {

    /**
     * find and replace must have same size
     *
     * @return copy of src, replace bytes if found and possible
     */
    public static byte[] replace(byte[] src, byte[] find, byte[] replace) {
        byte[] clone = Arrays.copyOf(src, src.length);
        if (find.length != replace.length) {
            return clone;
        }
        int findIndex = BytesUtils.indexOf(clone, find);
        if (findIndex > 0) {
            System.arraycopy(replace, 0, clone, findIndex, replace.length);
        }
        return clone;
    }

    /**
     * Returns the index within src of the first occurrence of the specified search bytes.
     * If no such value of {@code k} exists, then {@code -1} is returned.
     *
     * @param search the bytes to search for.
     * @return the index of the first occurrence of the specified bytes, or {@code -1} if there is no such occurrence.
     */
    public static int indexOf(byte[] src, byte[] search) {
        for (int i = 0; i < src.length - search.length + 1; ++i) {
            boolean found = true;
            for (int j = 0; j < search.length; ++j) {
                if (src[i + j] != search[j]) {
                    found = false;
                    break;
                }
            }
            if (found) {
                return i;
            }
        }
        return -1;
    }


    /**
     * @param search the bytes to search for.
     * @return true if the specified bytes exist in src, or {@code false} if there is no such occurrence.
     */
    public static boolean isExist(byte[] src, byte[] search) {
        return BytesUtils.indexOf(src, search) >= 0;
    }
}
