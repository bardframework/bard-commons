package org.bardframework.commons.web.http;

import org.bardframework.commons.utils.IOUtils;
import org.bardframework.commons.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public final class HttpCallerUtils {
    protected static final Logger LOGGER = LoggerFactory.getLogger(HttpCallerUtils.class);

    public static HttpCallResult httpCall(String url, String httpMethod, String bodyTemplate, Map<String, String> variables, Map<String, String> headers) throws IOException {
        HttpURLConnection connection = null;
        try {
            url = HttpCallerUtils.fillUrl(url, variables);
            connection = (HttpURLConnection) new URL(url).openConnection();
            // optional default is GET
            connection.setRequestMethod(httpMethod);
            if (null != headers) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            if (null != bodyTemplate && ("PUT".equalsIgnoreCase(httpMethod) || "POST".equalsIgnoreCase(httpMethod))) {
                LOGGER.debug("http method is [{}] setting request body", httpMethod);
                connection.setDoOutput(true);
                try (OutputStream outputStream = connection.getOutputStream()) {
                    outputStream.write(HttpCallerUtils.fillBody(bodyTemplate, variables).getBytes(StandardCharsets.UTF_8));
                }
            } else {
                LOGGER.debug("http method is not PUT or POST or request body is null, not set body");
            }
            /*
                getResponseCode to hack to force HttpURLConnection to run the request
                Otherwise getErrorStream always returns null
             */
            int responseCode = connection.getResponseCode();
            InputStream stream = null != connection.getErrorStream() ? connection.getErrorStream() : connection.getInputStream();
            byte[] response = IOUtils.toByteArray(stream);
            LOGGER.debug("http call[{}] response, code: [{}], details: [{}]", url, responseCode, IOUtils.toString(response, StandardCharsets.UTF_8.displayName()));
            return new HttpCallResult(responseCode, response, null != connection.getErrorStream());
        } finally {
            if (null != connection) {
                connection.disconnect();
            }
        }
    }

    private static String fillUrl(String urlTemplate, Map<String, String> variables) throws UnsupportedEncodingException {
        String result = String.valueOf(urlTemplate);
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            if (StringUtils.isBlank(entry.getKey())) {
                LOGGER.debug("one key entry of variables is empty, ignoring it");
                continue;
            }
            if (null == entry.getValue()) {
                LOGGER.debug("value of entry[{}] is null, ignoring it", entry.getKey());
                continue;
            }
            result = result.replaceAll(entry.getKey(), URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.displayName()));
        }
        return result;
    }

    private static String fillBody(String bodyTemplate, Map<String, String> variables) {
        String result = String.valueOf(bodyTemplate);
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            if (StringUtils.isBlank(entry.getKey())) {
                LOGGER.debug("one key entry of variables is empty, ignoring it");
                continue;
            }
            if (null == entry.getValue()) {
                LOGGER.debug("value of entry[{}] is null, ignoring it", entry.getKey());
                continue;
            }
            result = result.replaceAll(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
