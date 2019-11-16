package org.bardframework.commons.web.cors;

import org.bardframework.commons.config.ConfigKey;

public enum CorsConfig implements ConfigKey {
    CORS_MAPPING("cors.mapping", false),
    CORS_ALLOWED_ORIGINS("cors.allowedOrigins", false),
    CORS_ALLOWED_METHODS("cors.allowedMethods", false),
    CORS_ALLOWED_HEADERS("cors.allowedHeaders", false),
    CORS_EXPOSED_HEADERS("cors.exposedHeaders", false),
    CORS_ALLOW_CREDENTIALS("cors.allowCredentials", false),
    CORS_MAX_AGE_SECOND("cors.maxAgeSecond", false),
    CORS_ENABLE("cors.enable", true, false),
    ;
    private final String key;
    private final Object defaultValue;
    private final boolean required;

    CorsConfig(String key, Object defaultValue, boolean required) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.required = required;
    }

    CorsConfig(String key, boolean required) {
        this(key, null, required);
    }

    CorsConfig(String key) {
        this(key, true);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Object getDefaultValue() {
        return defaultValue;
    }

    @Override
    public boolean isRequired() {
        return required;
    }
}
