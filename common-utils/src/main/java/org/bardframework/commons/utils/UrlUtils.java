package org.bardframework.commons.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vahid Zafari on 8/12/2016.
 */
@UtilityClass
public final class UrlUtils {

    public static String makeCleanUrl(String... parts) {
        if (null == parts) {
            throw new IllegalArgumentException("null string array not acceptable");
        }
        StringBuilder all = new StringBuilder();
        for (String part : parts) {
            if (StringUtils.isNotBlank(part)) {
                all.append(part);
                all.append('/');
            }
        }
        String url = all.toString().replaceFirst("://", "%%%%");
        while (url.contains("//")) {
            url = url.replace("//", "/");
        }
        url = Strings.CS.removeEnd(url, "/");
        url = Strings.CS.removeStart(url, "/");
        return url.replaceFirst("%%%%", "://").replaceFirst("///", "//");
    }

    /**
     * Url encode a value using UTF-8 encoding.
     *
     * @param value the value to encode.
     * @return the encoded value.
     */
    public static String urlEncode(String value) {
        if (StringUtils.isBlank(value)) {
            return value;
        }
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    public static String fillUrlTemplate(String urlTemplate, Map<String, ?> args) {
        return StringTemplateUtils.fillTemplate(urlTemplate, UrlUtils.urlEncodeValues(args));
    }

    public static String fillUrlTemplate(String urlTemplate, String prefix, String suffix, Map<String, Object> args) {
        return StringTemplateUtils.fillTemplate(urlTemplate, prefix, suffix, UrlUtils.urlEncodeValues(args));
    }

    public static Map<String, String> urlEncodeValues(Map<String, ?> args) {
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<String, ?> entry : args.entrySet()) {
            String value = StringTemplateUtils.fillTemplate(entry.getValue().toString(), args);
            map.put(entry.getKey(), UrlUtils.urlEncode(value));
        }
        return map;
    }
}
