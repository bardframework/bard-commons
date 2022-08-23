package org.bardframework.commons.monitoring;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

public class MonitoringConfiguration {

    @Bean
    SecurityFilterChain monitoringSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.requestMatchers(configurer -> configurer.antMatchers("/actuator", "/actuator/**"))
                .authorizeRequests(registry -> registry.anyRequest().permitAll());
        return httpSecurity.build();
    }
}
