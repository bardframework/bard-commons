package org.bardframework.commons.swagger;

import org.bardframework.commons.Configurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationScopeBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.ant;

@Configuration
@EnableSwagger2
public class SwaggerConfigurator implements WebMvcConfigurer, Configurator {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerConfigurator.class);

    @Bean
    public Docket customImplementation() {
        return (new Docket(DocumentationType.SPRING_WEB)).apiInfo(this.apiInfo()).directModelSubstitute(LocalDateTime.class, String.class).directModelSubstitute(LocalDate.class, String.class);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API Info")
                .description("API Documentation")
                .termsOfServiceUrl("#")
                .contact(new Contact("Vahid Zafari", "https://www.linkedin.com/in/vzafari", "v.zafari@chmail.ir"))
                .version("1.0")
                .build();
    }

    @Bean
    SecurityContext securityContext() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = new AuthorizationScopeBuilder()
                .scope("global")
                .description("accessEverything")
                .build();

        SecurityReference securityReference = SecurityReference.builder()
                .reference("Authorization")
                .scopes(authorizationScopes)
                .build();

        return SecurityContext.builder()
                .securityReferences(newArrayList(securityReference))
                .forPaths(ant("/**"))
                .build();
    }

    @Bean
    SecurityScheme apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("/WEB-INF/assets/");
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        try {
            httpSecurity.authorizeRequests().antMatchers("/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/v2/api-docs").permitAll();
        } catch (Exception e) {
            LOGGER.error("error configuring swagger", e);
        }
    }
}
