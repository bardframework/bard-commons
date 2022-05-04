package org.bardframework.commons.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface SecurityConfigurer {
    void configure(HttpSecurity httpSecurity);
}
