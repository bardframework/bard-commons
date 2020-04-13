package org.bardframework.commons.config;

public interface ConfigKey {
    String getKey();

    Object getDefaultValue();

    boolean isRequired();
}
