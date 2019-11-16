package org.bardframework.commons.web.cors;

import org.bardframework.commons.config.ReloadableConfig;

public class CorsConfiguratorReloadable extends CorsConfigurator {

    @Override
    protected CorsFilter getCorsFilter() {
        return new CorsFilterReloadable(ReloadableConfig.getList(CorsConfig.CORS_MAPPING));
    }
}
