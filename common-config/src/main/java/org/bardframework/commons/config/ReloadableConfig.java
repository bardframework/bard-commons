package org.bardframework.commons.config;

import org.apache.commons.lang3.StringUtils;
import org.bardframework.commons.utils.StringTemplateUtils;
import org.bardframework.commons.web.utils.ResourceUtils;
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
    private static Collection<Class<? extends ConfigKey>> configClasses;
    private final Collection<String> reloadableConfigPath;

    public ReloadableConfig(Collection<String> reloadableConfigPath, Collection<Class<? extends ConfigKey>> configClasses) throws IOException {
        LOGGER.debug("reloadableConfigPath [{}], config entries: [{}]", Arrays.deepToString(reloadableConfigPath.toArray()), configClasses);
        this.reloadableConfigPath = reloadableConfigPath;
        ReloadableConfig.configClasses = configClasses;
        this.reload();
    }

    public static Collection<Class<? extends ConfigKey>> getConfigClasses() {
        return configClasses;
    }

    public static String get(ConfigKey<?, ?> key) {
        return ReloadableConfig.get(key, Map.of());
    }

    public static String get(ConfigKey<?, ?> key, Map<String, String> args) {
        String value = config.getProperty(key.getKey());
        value = StringTemplateUtils.fillTemplate(value, args);
        return value;
    }

    public static String get(ConfigKey<?, ?> key, String defaultValue) {
        return ReloadableConfig.get(key, defaultValue, Map.of());
    }

    public static String get(ConfigKey<?, ?> key, String defaultValue, Map<String, String> args) {
        String value = config.getProperty(key.getKey());
        value = StringTemplateUtils.fillTemplate(value, args);
        return null == value ? defaultValue : value;
    }

    public static List<String> getList(ConfigKey<?, ?> key) {
        return ReloadableConfig.getList(key, Map.of());
    }

    /**
     * @return list of separated value with ',', empty list if value not set.
     */
    public static List<String> getList(ConfigKey<?, ?> key, Map<String, String> args) {
        String value = config.getProperty(key.getKey());
        value = StringTemplateUtils.fillTemplate(value, args);
        return StringUtils.isBlank(value) ? Collections.emptyList() : List.of(value.split(","));
    }

    public static Set<String> getSet(ConfigKey<?, ?> key) {
        return ReloadableConfig.getSet(key, Map.of());
    }

    /**
     * @return Set of separated value with ',', empty Set if value not set.
     */
    public static Set<String> getSet(ConfigKey<?, ?> key, Map<String, String> args) {
        String value = config.getProperty(key.getKey());
        value = StringTemplateUtils.fillTemplate(value, args);
        return StringUtils.isBlank(value) ? Collections.emptySet() : Stream.of(value.split(",")).collect(Collectors.toSet());
    }

    public static int getInt(ConfigKey<?, ?> key, Map<String, String> args) {
        String value = config.getProperty(key.getKey());
        value = StringTemplateUtils.fillTemplate(value, args);
        return Integer.parseInt(value);
    }

    public static int getInt(ConfigKey<?, ?> key) {
        String value = config.getProperty(key.getKey());
        return Integer.parseInt(value);
    }

    public static Integer getInteger(ConfigKey<?, ?> key, Integer defaultValue) {
        return ReloadableConfig.getInteger(key, defaultValue, Map.of());
    }

    public static Integer getInteger(ConfigKey<?, ?> key, Integer defaultValue, Map<String, String> args) {
        String value = config.getProperty(key.getKey());
        value = StringTemplateUtils.fillTemplate(value, args);
        return null == value ? defaultValue : Integer.valueOf(value);
    }

    public static long getLong(ConfigKey<?, ?> key) {
        String value = config.getProperty(key.getKey());
        return Long.parseLong(value);
    }

    public static Long getLong(ConfigKey<?, ?> key, Long defaultValue) {
        return ReloadableConfig.getLong(key, defaultValue, Map.of());
    }

    public static Long getLong(ConfigKey<?, ?> key, Long defaultValue, Map<String, String> args) {
        String value = config.getProperty(key.getKey());
        value = StringTemplateUtils.fillTemplate(value, args);
        return null == value ? defaultValue : Long.valueOf(value);
    }

    public static boolean getBoolean(ConfigKey<?, ?> key) {
        String value = config.getProperty(key.getKey());
        return Boolean.parseBoolean(value);
    }

    public static Boolean getBoolean(ConfigKey<?, ?> key, Map<String, String> args) {
        String value = config.getProperty(key.getKey());
        value = StringTemplateUtils.fillTemplate(value, args);
        return Boolean.parseBoolean(value);
    }

    public static Boolean getBoolean(ConfigKey<?, ?> key, Boolean defaultValue) {
        return ReloadableConfig.getBoolean(key, defaultValue, Map.of());
    }

    public static Boolean getBoolean(ConfigKey<?, ?> key, Boolean defaultValue, Map<String, String> args) {
        String value = config.getProperty(key.getKey());
        value = StringTemplateUtils.fillTemplate(value, args);
        return StringUtils.isBlank(value) ? defaultValue : Boolean.valueOf(value);
    }

    public static short getShort(ConfigKey<?, ?> key) {
        String value = config.getProperty(key.getKey());
        return Short.parseShort(value);
    }

    public static Short getShort(ConfigKey<?, ?> key, Short defaultValue) {
        return ReloadableConfig.getShort(key, defaultValue, Map.of());
    }

    public static Short getShort(ConfigKey<?, ?> key, Short defaultValue, Map<String, String> args) {
        String value = config.getProperty(key.getKey());
        value = StringTemplateUtils.fillTemplate(value, args);
        return null == value ? defaultValue : Short.valueOf(value);
    }

    @Scheduled(cron = "${reloadableConfig.cron:*/30 * * * * *}")
    private void reload() throws IOException {
        Properties newConfigs = new Properties();
        List<Resource> resources = new ArrayList<>();
        for (String path : reloadableConfigPath) {
            resources.addAll(List.of(ResourceUtils.getResources(path)));
        }
        for (Resource resource : resources) {
            LOGGER.debug("loading global config from url [{}]", resource.getURL());
            if (!resource.exists()) {
                LOGGER.error("can't read config file in given url [{}], reload config ignored", resource.getURL());
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
        LOGGER.debug("load [{}] config entries successfully from [{}] files", newConfigs.keySet().size(), resources.size());
    }

    /**
     * check key requirements and set default values if need
     *
     * @param newConfigs config read from files
     * @return list of errors in configs
     */
    private List<String> check(Properties newConfigs) {
        List<String> errors = new ArrayList<>();
        for (Class<? extends ConfigKey> aClass : configClasses) {
            for (ConfigKey<?, ?> key : aClass.getEnumConstants()) {
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
