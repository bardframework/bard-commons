package org.bardframework.commons.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

public class SwaggerConfiguration {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder().group("public-api").pathsToMatch("/**").build();
    }

    @Bean
    public OpenAPI api() {
        Contact contact = new Contact().name("Vahid Zafari").url("https://www.linkedin.com/in/vzafari").email("va.zafari@gmail.com");
        Info info = new Info().title("API Info").description("API Documentation").contact(contact);
        return new OpenAPI().info(info);
    }

    @Bean
    SecurityFilterChain swaggerSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.requestMatchers(configurer -> configurer.antMatchers("/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**"))
                .authorizeRequests(registry -> registry.anyRequest().permitAll())
                .build();
    }
}
