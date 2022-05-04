package org.bardframework.commons.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public final class StringTemplateUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringTemplateUtils.class);

    private StringTemplateUtils() {
        /*
            prevent instantiation
         */
    }

    public static String fillTemplate(String template, Map<String, String> args) {
        return StringTemplateUtils.fillTemplate(template, "::", args);
    }

    public static String fillTemplate(String template, String prefixSuffix, Map<String, String> args) {
        if (StringUtils.isBlank(template)) {
            return template;
        }
        String result = template;
        for (Map.Entry<String, String> entry : args.entrySet()) {
            if (null == entry.getValue()) {
                LOGGER.debug("value of entry[{}] is null, ignoring it", entry.getKey());
                continue;
            }
            result = result.replaceAll(prefixSuffix + entry.getKey() + prefixSuffix, entry.getValue());
        }
        return result;
    }
}
