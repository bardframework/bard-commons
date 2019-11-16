package org.bardframework.commons.web.cors;

import org.bardframework.commons.config.ReloadableConfig;

import java.util.List;

public class CorsFilterReloadable extends CorsFilter {

    public CorsFilterReloadable(List<String> corsMapping) {
        super(corsMapping);
    }

    @Override
    protected boolean isEnable() {
        return ReloadableConfig.getBoolean(CorsConfig.CORS_ENABLE, true);
    }

    @Override
    public List<String> getAllowedOrigins() {
        return ReloadableConfig.getList(CorsConfig.CORS_ALLOWED_ORIGINS);
    }

    @Override
    public List<String> getAllowedMethods() {
        return ReloadableConfig.getList(CorsConfig.CORS_ALLOWED_METHODS);
    }

    @Override
    public List<String> getAllowedHeaders() {
        return ReloadableConfig.getList(CorsConfig.CORS_ALLOWED_HEADERS);
    }

    @Override
    public boolean isAllowedCredentials() {
        return ReloadableConfig.getBoolean(CorsConfig.CORS_ALLOW_CREDENTIALS, false);
    }

    @Override
    public List<String> getExposedHeaders() {
        return ReloadableConfig.getList(CorsConfig.CORS_EXPOSED_HEADERS);
    }

    @Override
    public int getMaxAge() {
        return ReloadableConfig.getInteger(CorsConfig.CORS_MAX_AGE_SECOND, 0);
    }
}
