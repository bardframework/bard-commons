package org.bardframework.commons.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vahid (va.zafari@gmail.com) on 08/08/19.
 */
public final class MapUtils {

    private MapUtils() {
        /*
            prevent instantiation
         */
    }

    /**
     * Returns a map containing five mappings.
     *
     * @param <K> the {@code Map}'s key type
     * @param k1  the first mapping's key
     * @param v1  the first mapping's value
     * @param k2  the second mapping's key
     * @param v2  the second mapping's value
     * @param k3  the third mapping's key
     * @param v3  the third mapping's value
     * @param k4  the fourth mapping's key
     * @param v4  the fourth mapping's value
     * @param k5  the fifth mapping's key
     * @param v5  the fifth mapping's value
     * @return a {@code Map} containing the specified mappings
     * @throws NullPointerException if any key  is {@code null}
     */
    public static <K> Map<K, Object> of(K k1, Object v1, K k2, Object v2, K k3, Object v3, K k4, Object v4, K k5, Object v5) {
        Map<K, Object> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        return map;
    }

    /**
     * Returns a map containing four mappings.
     *
     * @param <K> the {@code Map}'s key type
     * @param k1  the first mapping's key
     * @param v1  the first mapping's value
     * @param k2  the second mapping's key
     * @param v2  the second mapping's value
     * @param k3  the third mapping's key
     * @param v3  the third mapping's value
     * @param k4  the fourth mapping's key
     * @param v4  the fourth mapping's value
     * @return a {@code Map} containing the specified mappings
     * @throws NullPointerException if any key  is {@code null}
     */
    public static <K> Map<K, Object> of(K k1, Object v1, K k2, Object v2, K k3, Object v3, K k4, Object v4) {
        Map<K, Object> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        return map;
    }

    /**
     * Returns a map containing three mappings.
     *
     * @param <K> the {@code Map}'s key type
     * @param k1  the first mapping's key
     * @param v1  the first mapping's value
     * @param k2  the second mapping's key
     * @param v2  the second mapping's value
     * @param k3  the third mapping's key
     * @param v3  the third mapping's value
     * @return a {@code Map} containing the specified mappings
     * @throws NullPointerException if any key  is {@code null}
     */
    public static <K> Map<K, Object> of(K k1, Object v1, K k2, Object v2, K k3, Object v3) {
        Map<K, Object> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        return map;
    }

    /**
     * Returns a map containing two mappings.
     *
     * @param <K> the {@code Map}'s key type
     * @param k1  the first mapping's key
     * @param v1  the first mapping's value
     * @param k2  the second mapping's key
     * @param v2  the second mapping's value
     * @return a {@code Map} containing the specified mappings
     * @throws NullPointerException if any key  is {@code null}
     */
    public static <K> Map<K, Object> of(K k1, Object v1, K k2, Object v2) {
        Map<K, Object> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        return map;
    }

    /**
     * Returns a map containing two mappings.
     *
     * @param <K> the {@code Map}'s key type
     * @param k1  the first mapping's key
     * @param v1  the first mapping's value
     * @return a {@code Map} containing the specified mappings
     * @throws NullPointerException if any key  is {@code null}
     */
    public static <K> Map<K, Object> of(K k1, Object v1) {
        Map<K, Object> map = new HashMap<>();
        map.put(k1, v1);
        return map;
    }
}
