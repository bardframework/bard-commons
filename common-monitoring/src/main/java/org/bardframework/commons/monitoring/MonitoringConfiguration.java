package org.bardframework.commons.monitoring;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@ConfigurationProperties(prefix = "bard.web.monitoring")
@Getter
@Setter
public class MonitoringConfiguration {

    private String enabled;

    @Bean
    SecurityFilterChain monitoringSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.securityMatchers(configurer -> configurer.requestMatchers(new AntPathRequestMatcher("/actuator/**")))
                .authorizeHttpRequests(registry -> {
                    if ("true".equals(enabled)) {
                        registry.anyRequest().permitAll();
                    } else {
                        registry.anyRequest().denyAll();
                    }
                }).build();
    }
}
