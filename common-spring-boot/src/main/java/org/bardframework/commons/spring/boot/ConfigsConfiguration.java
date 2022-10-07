package org.bardframework.commons.spring.boot;

import org.apache.commons.lang3.StringUtils;
import org.bardframework.commons.utils.CharsetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ConfigsConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigsConfiguration.class);
    private static final String CLASS_PATH_KEY = "java.class.path";
    private static final String SEPARATOR_KEY = "path.separator";
    private static final Set<String> SENSITIVE_KEYS = new HashSet<>(Arrays.asList("password", "credential", "secret"));
    private static final Set<String> NOT_LOG_KEYS = new HashSet<>(Collections.singletonList(CLASS_PATH_KEY));

    @Bean
    static PropertySourcesPlaceholderConfigurer placeHolderConfigurer(Environment environment) throws IOException {
        PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        List<Resource> resources = new ArrayList<>(Arrays.asList(patternResolver.getResources("classpath*:**/**-config.properties")));
        resources.addAll(Arrays.asList(patternResolver.getResources("classpath*:**/**/application.properties")));
        for (String profile : environment.getActiveProfiles()) {
            resources.addAll(Arrays.asList(patternResolver.getResources("classpath*:**/**/application-" + profile + ".properties")));
        }
        ConfigsConfiguration.logConfigs(environment, resources);
        PropertySourcesPlaceholderConfigurer propertyConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertyConfigurer.setLocations(resources.toArray(new Resource[0]));
        return propertyConfigurer;
    }

    private static void logConfigs(Environment environment, List<Resource> resources) {
        final MutablePropertySources sources = ((AbstractEnvironment) environment).getPropertySources();
        StringBuilder configs = new StringBuilder();
        ConfigsConfiguration.append(configs, "Active profiles", Arrays.toString(environment.getActiveProfiles()));
        String classpath = environment.getProperty(CLASS_PATH_KEY);
        String separator = environment.getProperty(SEPARATOR_KEY);
        ConfigsConfiguration.append(configs, "Config Files", resources.stream().map(Object::toString).collect(Collectors.joining("\n\t")));
        if (StringUtils.isNotBlank(classpath) && StringUtils.isNotBlank(separator)) {
            ConfigsConfiguration.append(configs, CLASS_PATH_KEY, Arrays.stream(classpath.split(separator)).map(Object::toString).collect(Collectors.joining("\n\t")));
        } else {
            LOGGER.info("classpath[{}] or separator[{}] is not valid", classpath, separator);
            ConfigsConfiguration.append(configs, CLASS_PATH_KEY, classpath);
        }
        ConfigsConfiguration.append(configs, "DefaultCharset", String.valueOf(CharsetUtils.getDefaultCharset()));
        ConfigsConfiguration.append(configs, "DefaultLocale", String.valueOf(CharsetUtils.getDefaultLocale()));
        ConfigsConfiguration.append(configs, "DefaultEncoding", String.valueOf(CharsetUtils.getDefaultEncoding()));

        StreamSupport.stream(sources.spliterator(), false)
                .filter(propertySource -> propertySource instanceof EnumerablePropertySource)
                .map(propertySource -> ((EnumerablePropertySource<?>) propertySource).getPropertyNames())
                .flatMap(Arrays::stream)
                .forEach(property -> {
                    if (NOT_LOG_KEYS.contains(property.toLowerCase())) {
                        /*
                            do nothing
                         */
                    } else if (SENSITIVE_KEYS.contains(property.toLowerCase())) {
                        ConfigsConfiguration.append(configs, property, "*****");
                    } else {
                        ConfigsConfiguration.append(configs, property, environment.getProperty(property));
                    }
                });
        LOGGER.info("\n\n====== configuration ======\n{}====== configuration ======\n", configs);
    }

    private static void append(StringBuilder configs, String key, String value) {
        configs.append(key);
        configs.append(":\n\t");
        configs.append(value);
        configs.append("\n");
    }
}
