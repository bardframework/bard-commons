package org.bardframework.commons.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vahid Zafari on 8/12/2016.
 */
public final class UrlUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlUtils.class);

    private UrlUtils() {
        /*
            prevent instantiation
         */
    }

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
        url = StringUtils.removeEnd(url, "/");
        url = StringUtils.removeStart(url, "/");
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
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.name());
        } catch (final UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }


    public static String fillUrlTemplate(String urlTemplate, Map<String, String> args) {
        return StringTemplateUtils.fillTemplate(urlTemplate, UrlUtils.urlEncodeValues(args));
    }

    public static String fillUrlTemplate(String urlTemplate, String prefix, String suffix, Map<String, String> args) {
        return StringTemplateUtils.fillTemplate(urlTemplate, prefix, suffix, UrlUtils.urlEncodeValues(args));
    }

    public static Map<String, String> urlEncodeValues(Map<String, String> args) {
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<String, String> entry : args.entrySet()) {
            String value = StringTemplateUtils.fillTemplate(entry.getValue(), args);
            map.put(entry.getKey(), UrlUtils.urlEncode(value));
        }
        return map;
    }
}
