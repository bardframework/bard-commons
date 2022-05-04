package org.bardframework.commons.web.cors;

import org.bardframework.commons.security.SecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import java.util.List;

public class CorsSecurityConfigurer implements SecurityConfigurer {

    private final List<String> corsMapping;
    private final List<String> corsAllowedOrigins;
    private final List<String> corsAllowedMethods;
    private final List<String> corsAllowedHeaders;
    private final List<String> corsExposedHeaders;
    private final boolean corsAllowCredentials;
    private final int corsMaxAge;

    public CorsSecurityConfigurer(List<String> corsMapping, List<String> corsAllowedOrigins, List<String> corsAllowedMethods, List<String> corsAllowedHeaders, List<String> corsExposedHeaders, boolean corsAllowCredentials, int corsMaxAge) {
        this.corsMapping = corsMapping;
        this.corsAllowedOrigins = corsAllowedOrigins;
        this.corsAllowedMethods = corsAllowedMethods;
        this.corsAllowedHeaders = corsAllowedHeaders;
        this.corsExposedHeaders = corsExposedHeaders;
        this.corsAllowCredentials = corsAllowCredentials;
        this.corsMaxAge = corsMaxAge;
    }

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

    protected CorsFilter getCorsFilter() {
        return new CorsFilter(this.getCorsMapping(), this.getCorsAllowedOrigins(), this.getCorsAllowedMethods(), this.getCorsAllowedHeaders(), this.getCorsExposedHeaders(), this.isCorsAllowCredentials(), this.getCorsMaxAge());
    }
}
