package org.bardframework.commons.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@ConfigurationProperties(prefix = "bard.web.swagger")
@Getter
@Setter
public class SwaggerConfiguration {

    private String enabled;

    @ConditionalOnMissingBean(GroupedOpenApi.class)
    @Bean
    GroupedOpenApi openApi() {
        return GroupedOpenApi.builder().group("public-api").pathsToMatch("/**").build();
    }

    @ConditionalOnMissingBean(OpenAPI.class)
    @Bean
    public OpenAPI openAPI(Info info) {
        String securitySchemeName = "bearerAuth";
        return new OpenAPI().addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components().addSecuritySchemes(securitySchemeName, new SecurityScheme().name(securitySchemeName).type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(info);
    }

    @ConditionalOnMissingBean(Info.class)
    @Bean
    public Info info() {
        Contact contact = new Contact().name("Bard Framework").url("https://github.com/bardframework").email("info@bardframework.org");
        return new Info().title("API Info").description("API Documentation").contact(contact);
    }

    @Bean
    SecurityFilterChain swaggerSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.securityMatcher("/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**")
                .authorizeHttpRequests(registry -> {
                    if ("true".equals(enabled)) {
                        registry.anyRequest().permitAll();
                    } else {
                        registry.anyRequest().denyAll();
                    }
                }).build();
    }
}
