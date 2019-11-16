package org.bardframework.commons.web.cors;

import org.bardframework.commons.Configurator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import java.util.List;

public class CorsConfigurator implements Configurator {

    @Value("${cors.mapping:#{null}}")
    private List<String> corsMapping;
    @Value("${cors.allowedOrigins:#{null}}")
    private List<String> corsAllowedOrigins;
    @Value("${cors.allowedMethods:#{null}}")
    private List<String> corsAllowedMethods;
    @Value("${cors.allowedHeaders:#{null}}")
    private List<String> corsAllowedHeaders;
    @Value("${cors.exposedHeaders:#{null}}")
    private List<String> corsExposedHeaders;
    @Value("${cors.allowCredentials:#{false}}")
    private boolean corsAllowCredentials;
    @Value("${cors.maxAgeSecond:#{30}}")
    private int corsMaxAge;
    @Value("${cors.enable:#{true}}")
    private boolean corsEnable;

    @Override
    public void configure(HttpSecurity httpSecurity) {
        httpSecurity.addFilterBefore(this.getCorsFilter(), ChannelProcessingFilter.class);
    }

    protected List<String> getCorsMapping() {
        return corsMapping;
    }

    protected List<String> getCorsAllowedOrigins() {
        return corsAllowedOrigins;
    }

    protected List<String> getCorsAllowedMethods() {
        return corsAllowedMethods;
    }

    protected List<String> getCorsAllowedHeaders() {
        return corsAllowedHeaders;
    }

    protected List<String> getCorsExposedHeaders() {
        return corsExposedHeaders;
    }

    protected boolean isCorsAllowCredentials() {
        return corsAllowCredentials;
    }

    protected int getCorsMaxAge() {
        return corsMaxAge;
    }

    public boolean isCorsEnable() {
        return corsEnable;
    }

    protected CorsFilter getCorsFilter() {
        return new CorsFilter(this.getCorsMapping(), this.getCorsAllowedOrigins(), this.getCorsAllowedMethods(), this.getCorsAllowedHeaders(), this.getCorsExposedHeaders(), this.isCorsAllowCredentials(), this.getCorsMaxAge(), this.isCorsEnable());
    }
}
