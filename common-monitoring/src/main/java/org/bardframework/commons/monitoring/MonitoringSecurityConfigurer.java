package org.bardframework.commons.monitoring;

import org.bardframework.commons.security.SecurityConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public class MonitoringSecurityConfigurer implements SecurityConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitoringSecurityConfigurer.class);

    @Override
    public void configure(HttpSecurity httpSecurity) {
        try {
            httpSecurity.authorizeRequests().antMatchers("/actuator", "/actuator/**").permitAll();
        } catch (Exception e) {
            LOGGER.error("error configuring monitoring", e);
        }
    }
}
