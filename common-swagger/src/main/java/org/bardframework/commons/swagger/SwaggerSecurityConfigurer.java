package org.bardframework.commons.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.bardframework.commons.security.SecurityConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerSecurityConfigurer implements WebMvcConfigurer, SecurityConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerSecurityConfigurer.class);

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-api")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Info")
                        .description("API Documentation")
                        .contact(new Contact()
                                .name("Vahid Zafari")
                                .url("https://www.linkedin.com/in/vzafari")
                                .email("va.zafari@gmail.com"))
                );
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        try {
            httpSecurity.authorizeRequests().antMatchers("/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**").permitAll();
        } catch (Exception e) {
            LOGGER.error("error configuring swagger", e);
        }
    }
}
