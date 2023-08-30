package org.bardframework.commons.spring.boot;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bardframework.commons.utils.CharsetUtils;
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

@Slf4j
public class ConfigsConfiguration {

    private static final String CLASS_PATH_KEY = "java.class.path";
    private static final String SEPARATOR_KEY = "path.separator";
    private static final Set<String> SENSITIVE_KEY_PARTS = new HashSet<>(List.of("password", "credential", "secret", "token"));
    private static final Set<String> NOT_LOG_KEYS = new HashSet<>(Collections.singletonList(CLASS_PATH_KEY));

    private static void append(StringBuilder configs, String key, String value) {
        configs.append(key);
        configs.append(":\n\t");
        configs.append(value);
        configs.append("\n");
    }

    @Bean
    BardPropertySourcesPlaceholderConfigurer placeHolderConfigurer(Environment environment) throws IOException {
        PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        List<Resource> resources = new ArrayList<>();
        resources.addAll(List.of(patternResolver.getResources("classpath*:**/**/application.properties")));
        resources.addAll(List.of(patternResolver.getResources("classpath*:**/**-config.properties")));
        for (String profile : environment.getActiveProfiles()) {
            resources.addAll(List.of(patternResolver.getResources("classpath*:**/**/application-" + profile + ".properties")));
        }
        BardPropertySourcesPlaceholderConfigurer propertyConfigurer = new BardPropertySourcesPlaceholderConfigurer();
        propertyConfigurer.setLocations(resources.toArray(new Resource[0]));
        return propertyConfigurer;
    }

    @Bean("appConfigs")
    Map<String, String> appConfigs(Environment environment, BardPropertySourcesPlaceholderConfigurer configurer) throws IOException {
        final MutablePropertySources sources = ((AbstractEnvironment) environment).getPropertySources();
        Map<String, String> configs = new HashMap<>();
        StreamSupport.stream(sources.spliterator(), false)
                .filter(propertySource -> propertySource instanceof EnumerablePropertySource)
                .map(propertySource -> ((EnumerablePropertySource<?>) propertySource).getPropertyNames())
                .flatMap(Arrays::stream)
                .forEach(property -> configs.put(property, environment.getProperty(property)));

        for (Map.Entry<Object, Object> entry : configurer.mergeProperties().entrySet()) {
            configs.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return configs;
    }

    @PostConstruct
    void logConfigs(Environment environment, List<Resource> resources, Map<String, String> appConfigs) {
        StringBuilder aggregatedConfig = new StringBuilder();
        ConfigsConfiguration.append(aggregatedConfig, "Active profiles", Arrays.toString(environment.getActiveProfiles()));
        String classpath = environment.getProperty(CLASS_PATH_KEY);
        String separator = environment.getProperty(SEPARATOR_KEY);
        ConfigsConfiguration.append(aggregatedConfig, "Config Files", resources.stream().map(Object::toString).collect(Collectors.joining("\n\t")));
        if (StringUtils.isNotBlank(classpath) && StringUtils.isNotBlank(separator)) {
            ConfigsConfiguration.append(aggregatedConfig, CLASS_PATH_KEY, Arrays.stream(classpath.split(separator)).map(Object::toString).collect(Collectors.joining("\n\t")));
        } else {
            log.info("classpath[{}] or separator[{}] is not valid", classpath, separator);
            ConfigsConfiguration.append(aggregatedConfig, CLASS_PATH_KEY, classpath);
        }
        ConfigsConfiguration.append(aggregatedConfig, "DefaultCharset", String.valueOf(CharsetUtils.getDefaultCharset()));
        ConfigsConfiguration.append(aggregatedConfig, "DefaultLocale", String.valueOf(CharsetUtils.getDefaultLocale()));
        ConfigsConfiguration.append(aggregatedConfig, "DefaultEncoding", String.valueOf(CharsetUtils.getDefaultEncoding()));

        appConfigs.keySet().stream().sorted().forEach(property -> {
            if (this.getNotLogKeys().contains(property.toLowerCase())) {
                /*
                    do nothing
                 */
            } else if (this.getSensitiveKeyParts().stream().anyMatch(sensitiveKey -> property.toLowerCase().contains(sensitiveKey))) {
                ConfigsConfiguration.append(aggregatedConfig, property, "*****");
            } else {
                ConfigsConfiguration.append(aggregatedConfig, property, appConfigs.get(property));
            }
        });

        log.info("\n\n====== configuration ======\n{}====== configuration ======\n", aggregatedConfig);
    }

    protected Set<String> getSensitiveKeyParts() {
        return SENSITIVE_KEY_PARTS;
    }

    protected Set<String> getNotLogKeys() {
        return NOT_LOG_KEYS;
    }

    public static class BardPropertySourcesPlaceholderConfigurer extends PropertySourcesPlaceholderConfigurer {
        @Override
        public Properties mergeProperties() throws IOException {
            return super.mergeProperties();
        }
    }
}
