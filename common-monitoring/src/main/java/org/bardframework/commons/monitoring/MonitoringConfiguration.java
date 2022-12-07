package org.bardframework.commons.monitoring;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

public class MonitoringConfiguration {

    @Bean
    SecurityFilterChain monitoringSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.securityMatcher("/actuator/**")
                .authorizeHttpRequests().anyRequest().permitAll()
                .and()
                .build();
    }
}
