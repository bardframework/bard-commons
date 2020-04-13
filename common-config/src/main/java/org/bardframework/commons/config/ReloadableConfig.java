package org.bardframework.commons.config;

import org.bardframework.commons.reflection.ReflectionUtils;
import org.bardframework.commons.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReloadableConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReloadableConfig.class);

    private static Properties config = new Properties();
    private final List<String> reloadableConfigPath;

    public ReloadableConfig(List<String> reloadableConfigPath) throws IOException {
        LOGGER.debug("reloadableConfigPath [{}]", Arrays.deepToString(reloadableConfigPath.toArray()));
        this.reloadableConfigPath = reloadableConfigPath;
        this.reload();
    }

    public static String get(ConfigKey key) {
        return config.getProperty(key.getKey());
    }

    public static String get(ConfigKey key, String defaultValue) {
        String value = config.getProperty(key.getKey());
        return null == value ? defaultValue : value;
    }

    /**
     * @param key
     * @return list of separated value with ',', empty list if value not set.
     */
    public static List<String> getList(ConfigKey key) {
        String value = config.getProperty(key.getKey());
        return StringUtils.isBlank(value) ? Collections.emptyList() : Arrays.asList(value.split(","));
    }

    /**
     * @param key
     * @return Set of separated value with ',', empty Set if value not set.
     */
    public static Set<String> getSet(ConfigKey key) {
        String value = config.getProperty(key.getKey());
        return StringUtils.isBlank(value) ? Collections.emptySet() : Stream.of(value.split(",")).collect(Collectors.toSet());
    }

    public static int getInt(ConfigKey key) {
        return Integer.parseInt(config.getProperty(key.getKey()));
    }

    public static Integer getInteger(ConfigKey key, Integer defaultValue) {
        String value = config.getProperty(key.getKey());
        return null == value ? defaultValue : Integer.valueOf(value);
    }

    public static long getLong(ConfigKey key) {
        return Long.parseLong(config.getProperty(key.getKey()));
    }

    public static Long getLong(ConfigKey key, Long defaultValue) {
        String value = config.getProperty(key.getKey());
        return null == value ? defaultValue : Long.valueOf(value);
    }

    public static boolean getBoolean(ConfigKey key) {
        return Boolean.parseBoolean(config.getProperty(key.getKey()));
    }

    public static Boolean getBoolean(ConfigKey key, Boolean defaultValue) {
        String value = config.getProperty(key.getKey());
        return StringUtils.isBlank(value) ? defaultValue : Boolean.valueOf(value);
    }

    public static short getShort(ConfigKey key) {
        return Short.parseShort(config.getProperty(key.getKey()));
    }

    public static Short getShort(ConfigKey key, Short defaultValue) {
        String value = config.getProperty(key.getKey());
        return null == value ? defaultValue : Short.valueOf(value);
    }

    @Scheduled(cron = "${reloadableConfig.cron:*/30 * * * * *}")
    private void reload() throws IOException {
        Properties newConfigs = new Properties();
        List<Resource> resources = new ArrayList<>();
        for (String path : reloadableConfigPath) {
            resources.addAll(Arrays.asList(ResourceUtils.getResources(path)));
        }
        for (Resource resource : resources) {
            LOGGER.debug("loading global config from uri [{}]", resource.getURI());
            if (!resource.exists()) {
                LOGGER.error("can't read config file in given uri [{}], reload config ignored", resource.getURI());
                return;
            }
            try (InputStream inputStream = resource.getInputStream()) {
                newConfigs.load(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            }
        }
        List<String> configErrors = this.check(newConfigs);
        if (!configErrors.isEmpty()) {
            StringBuilder log = new StringBuilder();
            log.append("\n**************************************************");
            log.append("\nconfig reload failed due errors, correct them and wait until next reload.\n");
            for (String configError : configErrors) {
                log.append("\n");
                log.append(configError);
            }
            log.append("\n**************************************************\n");
            LOGGER.error("config error {}", log);

            /*
                first config load, app cant't start via config error.
             */
            if (config.isEmpty()) {
                throw new IllegalStateException("config error, see previous error");
            } else {
                /*
                    use previous configs, new configs ignored
                 */
                return;
            }
        }
        ReloadableConfig.config = newConfigs;
        LOGGER.info("load [{}] config entries successfully from [{}] files", newConfigs.keySet().size(), resources.size());
    }

    /**
     * check key requirements and set default values if need
     *
     * @param newConfigs config read from files
     * @return list of errors in configs
     */
    private List<String> check(Properties newConfigs) {
        List<String> errors = new ArrayList<>();
        for (Class<? extends ConfigKey> aClass : ReflectionUtils.getSubTypeOf(ConfigKey.class)) {
            if (!aClass.isEnum()) {
                continue;
            }
            for (ConfigKey key : aClass.getEnumConstants()) {
                String value = newConfigs.getProperty(key.getKey());
                if (key.isRequired()) {
                    if (StringUtils.isBlank(value)) {
                        errors.add(String.format("[%s] config is required but not present in any config files", key.getKey()));
                    }
                    //TODO check type
                } else {
                    Object defaultValue = key.getDefaultValue();
                    if (null != defaultValue && StringUtils.isBlank(value)) {
                        newConfigs.put(key.getKey(), defaultValue.toString());
                    }
                }
            }
        }
        return errors;
    }
}
