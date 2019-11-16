package org.bardframework.commons.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Created by Vahid Zafari on 8/12/2016.
 */
public final class UrlUtils {

    private UrlUtils() {
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
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.name());
        } catch (final UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
