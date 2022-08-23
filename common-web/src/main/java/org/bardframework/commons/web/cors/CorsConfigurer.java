package org.bardframework.commons.web.cors;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import java.util.List;

public class CorsConfigurer {
    private final CorsFilter corsFilter;

    public CorsConfigurer(List<String> corsMapping, List<String> corsAllowedOrigins, List<String> corsAllowedMethods, List<String> corsAllowedHeaders, List<String> corsExposedHeaders, boolean corsAllowCredentials, int corsMaxAge) {
        this.corsFilter = new CorsFilter(corsMapping, corsAllowedOrigins, corsAllowedMethods, corsAllowedHeaders, corsExposedHeaders, corsAllowCredentials, corsMaxAge);
    }

    public void addCorsFilter(HttpSecurity httpSecurity) {
        httpSecurity.requestMatcher(this.getCorsFilter().getCorsRequestMatchers()).addFilterBefore(this.getCorsFilter(), org.springframework.web.filter.CorsFilter.class);
    }

    public CorsFilter getCorsFilter() {
        return corsFilter;
    }

}
