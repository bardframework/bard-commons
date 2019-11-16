package org.bardframework.commons.spring.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.StreamSupport;

public interface SpringBootRunner {

    static void run(Class<?> applicationClass, String[] args) {
        SpringApplication.run(applicationClass, args);
    }

    @Bean
    static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() throws IOException {
        PropertySourcesPlaceholderConfigurer propertyConfigurer = new PropertySourcesPlaceholderConfigurer();
        List<Resource> resources = new ArrayList<>(Arrays.asList(new PathMatchingResourcePatternResolver().getResources("classpath*:**/**-config.properties")));
        resources.addAll(Arrays.asList(new PathMatchingResourcePatternResolver().getResources("classpath*:**/**/application.properties")));
        propertyConfigurer.setLocations(resources.toArray(new Resource[0]));
        return propertyConfigurer;
    }

    @EventListener
    default void handleContextRefresh(ContextRefreshedEvent event) {
        final Environment environment = event.getApplicationContext().getEnvironment();
        Logger logger = LoggerFactory.getLogger("App Config");
        final MutablePropertySources sources = ((AbstractEnvironment) environment).getPropertySources();
        StringBuilder configs = new StringBuilder();
        configs.append("Active profiles");
        configs.append("=");
        configs.append(Arrays.toString(environment.getActiveProfiles()));
        configs.append("\n");

        StreamSupport.stream(sources.spliterator(), false)
                .filter(propertySource -> propertySource instanceof EnumerablePropertySource)
                .map(propertySource -> ((EnumerablePropertySource) propertySource).getPropertyNames())
                .flatMap(Arrays::stream)
                .forEach(property -> {
                    configs.append(property);
                    configs.append("=");
                    configs.append((property.toLowerCase().contains("credential") || property.toLowerCase().contains("password")) ? "*****" : environment.getProperty(property));
                    configs.append("\n");
                });
        configs.append("DefaultCharset=");
        configs.append(CharsetUtils.getDefaultCharset());
        configs.append("\nDefaultLocale=");
        configs.append(CharsetUtils.getDefaultLocale());
        configs.append("\nDefaultEncoding=");
        configs.append(CharsetUtils.getDefaultEncoding());
        logger.info("\n\n====== configuration ======\n{}\n====== configuration ======\n", configs);
    }
}
