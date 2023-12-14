package org.bardframework.commons.monitoring;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class MonitoringConfiguration {

    @Bean
    SecurityFilterChain monitoringSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.securityMatchers(configurer -> configurer.requestMatchers(new AntPathRequestMatcher("/actuator/**")));
        httpSecurity.authorizeHttpRequests(registry -> registry.anyRequest().permitAll());
        return httpSecurity.build();
    }
}
