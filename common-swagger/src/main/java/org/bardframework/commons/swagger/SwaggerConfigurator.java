package org.bardframework.commons.swagger;

import org.bardframework.commons.config.Configurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfigurator implements WebMvcConfigurer, Configurator {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerConfigurator.class);

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API Info")
                .description("API Documentation")
                .termsOfServiceUrl("#")
                .contact(new Contact("Vahid Zafari", "https://www.linkedin.com/in/vzafari", "v.zafari@chmail.ir"))
                .build();
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        try {
            httpSecurity.authorizeRequests().antMatchers("/swagger-resources/**", "/swagger-ui/**", "/v2/api-docs", "/v3/api-docs").permitAll();
        } catch (Exception e) {
            LOGGER.error("error configuring swagger", e);
        }
    }
}
