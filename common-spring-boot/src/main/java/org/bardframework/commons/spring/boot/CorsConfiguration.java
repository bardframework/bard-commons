package org.bardframework.commons.spring.boot;

import org.bardframework.commons.web.cors.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

import java.util.List;

public class CorsConfiguration {
    private final CorsFilter corsFilter;

    public CorsConfiguration(List<String> corsMapping, List<String> corsAllowedOrigins, List<String> corsAllowedMethods, List<String> corsAllowedHeaders, List<String> corsExposedHeaders, boolean corsAllowCredentials, int corsMaxAge) {
        this.corsFilter = new CorsFilter(corsMapping, corsAllowedOrigins, corsAllowedMethods, corsAllowedHeaders, corsExposedHeaders, corsAllowCredentials, corsMaxAge);
    }

    public CorsFilter getCorsFilter() {
        return corsFilter;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean() {
        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>(this.getCorsFilter());
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        return registrationBean;
    }

}
