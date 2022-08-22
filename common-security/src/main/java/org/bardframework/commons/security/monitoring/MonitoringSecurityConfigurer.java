package org.bardframework.commons.security.monitoring;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

public class MonitoringSecurityConfigurer {

    @Bean
    SecurityFilterChain monitoringSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers("/actuator", "/actuator/**").permitAll();
        return httpSecurity.build();
    }
}
