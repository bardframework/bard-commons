package org.bardframework.commons.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

public class SwaggerConfiguration {

    @Bean
    GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder().group("public-api").pathsToMatch("/**").build();
    }

    @Bean
    OpenAPI api() {
        Contact contact = new Contact().name("Bardframework").url("https://github.com/bardframework").email("info@bardframework.org");
        Info info = new Info().title("API Info").description("API Documentation").contact(contact);
        return new OpenAPI().info(info);
    }

    @Bean
    SecurityFilterChain swaggerSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.securityMatcher("/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**");
        httpSecurity.authorizeHttpRequests(registry -> registry.anyRequest().permitAll());
        return httpSecurity.build();
    }
}
