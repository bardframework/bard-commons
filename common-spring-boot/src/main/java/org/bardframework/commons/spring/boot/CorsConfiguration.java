package org.bardframework.commons.spring.boot;

import lombok.Getter;
import lombok.Setter;
import org.bardframework.commons.web.cors.CorsFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

import java.util.List;

@ConfigurationProperties(prefix = "bard.web.cors")
@Getter
@Setter
public class CorsConfiguration {

    private List<String> mapping;
    private List<String> allowedOrigins;
    private List<String> allowedMethods;
    private List<String> allowedHeaders;
    private List<String> exposedHeaders;
    private boolean allowCredentials;
    private int maxAgeSecond;

    @ConditionalOnProperty(value = "bard.web.cors.enabled", havingValue = "true")
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean() {
        CorsFilter corsFilter = new CorsFilter(mapping, allowedOrigins, allowedMethods, allowedHeaders, exposedHeaders, allowCredentials, maxAgeSecond);
        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>(corsFilter);
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        return registrationBean;
    }
}
