package org.bardframework.commons.config;

import java.util.Map;

public interface ConfigKey<T, C extends Enum<C>> {
    String getKey();

    Object getDefaultValue();

    boolean isRequired();

    default void fill(T data, Map<String, String> args) {

    }
}
