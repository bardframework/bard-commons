package org.bardframework.commons.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;

import java.util.Map;

public class StringTemplateUtils {

    public static String fillTemplate(String template, Map<String, ?> args) {
        return StringTemplateUtils.fillTemplate(template, "::", "::", args);
    }

    public static String fillTemplate(String template, String prefix, String suffix, Map<String, ?> args) {
        if (StringUtils.isBlank(template)) {
            return template;
        }
        return new StringSubstitutor(args, prefix, suffix).replace(template);
    }
}
