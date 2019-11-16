package org.bardframework.commons.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vahid Zafari on 8/12/2016.
 */
public final class CollectionUtils extends org.springframework.util.CollectionUtils {

    private CollectionUtils() {
    }

    /**
     * @param values array that must be convert to Set
     * @param <T>
     * @return new HashSet containing args array.
     */
    public static <T> Set<T> toSet(T... values) {
        Set<T> set = new HashSet<>();
        if (null != values) {
            for (T value : values) {
                set.add(value);
            }
        }
        return set;
    }

    /**
     * Return {@code true} if the supplied Collection is {@code not null} or empty.
     * Otherwise, return {@code false}.
     *
     * @param collection the Collection to check
     * @return whether the given Collection is not null or empty
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

}
