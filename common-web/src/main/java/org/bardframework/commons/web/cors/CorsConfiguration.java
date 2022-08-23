package org.bardframework.commons.web.cors;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

public class CorsConfiguration {
    private final CorsFilter corsFilter;

    public CorsConfiguration(List<String> corsMapping, List<String> corsAllowedOrigins, List<String> corsAllowedMethods, List<String> corsAllowedHeaders, List<String> corsExposedHeaders, boolean corsAllowCredentials, int corsMaxAge) {
        this.corsFilter = new CorsFilter(corsMapping, corsAllowedOrigins, corsAllowedMethods, corsAllowedHeaders, corsExposedHeaders, corsAllowCredentials, corsMaxAge);
    }

    @Bean
    SecurityFilterChain corsSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.requestMatcher(corsFilter.getCorsRequestMatchers()).addFilterBefore(corsFilter, org.springframework.web.filter.CorsFilter.class);
        return httpSecurity.build();
    }
}
