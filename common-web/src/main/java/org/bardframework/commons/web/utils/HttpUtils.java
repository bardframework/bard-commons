package org.bardframework.commons.web.utils;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bardframework.commons.utils.StringTemplateUtils;
import org.bardframework.commons.utils.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public final class HttpUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

    private HttpUtils() {
        /*
            prevent instantiation
         */
    }

    public static HttpCallResult httpCall(String httpMethod, String urlTemplate, String bodyTemplate, Map<String, String> headers, Map<String, String> args) throws IOException {
        if (StringUtils.isBlank(httpMethod)) {
            throw new IllegalStateException("empty http method not acceptable");
        }
        if (StringUtils.isBlank(urlTemplate)) {
            throw new IllegalStateException("empty url not acceptable");
        }
        HttpURLConnection connection = null;
        try {
            urlTemplate = UrlUtils.fillUrlTemplate(urlTemplate, args);
            connection = (HttpURLConnection) new URL(urlTemplate).openConnection();
            // optional default is GET
            connection.setRequestMethod(httpMethod);
            if (MapUtils.isNotEmpty(headers)) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    if (null == entry.getValue()) {
                        LOGGER.debug("value of header [{}] is null, ignoring it", entry.getKey());
                        continue;
                    }
                    String headerValue = StringTemplateUtils.fillTemplate(entry.getValue(), args);
                    connection.setRequestProperty(entry.getKey(), headerValue);
                }
            }
            if (StringUtils.isNotBlank(bodyTemplate)) {
                LOGGER.debug("setting request body, http method [{}] ", httpMethod);
                connection.setDoOutput(true);
                String body = StringTemplateUtils.fillTemplate(bodyTemplate, args);
                try (OutputStream outputStream = connection.getOutputStream()) {
                    outputStream.write(body.getBytes(StandardCharsets.UTF_8));
                }
            }
            /*
                getResponseCode to hack to force HttpURLConnection to run the request
                Otherwise getErrorStream always returns null
             */
            int responseCode = connection.getResponseCode();
            InputStream stream = null != connection.getErrorStream() ? connection.getErrorStream() : connection.getInputStream();
            byte[] response = IOUtils.toByteArray(stream);
            LOGGER.debug("http call[{}] response, code: [{}], details: [{}]", urlTemplate, responseCode, IOUtils.toString(response, StandardCharsets.UTF_8.displayName()));
            return new HttpCallResult(responseCode, response, null != connection.getErrorStream());
        } finally {
            if (null != connection) {
                connection.disconnect();
            }
        }
    }
}


