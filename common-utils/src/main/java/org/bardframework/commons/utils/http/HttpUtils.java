package org.bardframework.commons.utils.http;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bardframework.commons.utils.StringTemplateUtils;
import org.bardframework.commons.utils.UrlUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@UtilityClass
public final class HttpUtils {

    public static HttpCallResponse httpCall(String httpMethod, String urlTemplate, String bodyTemplate, int connectTimeoutSeconds, int readTimeoutSeconds, Map<String, String> headers, Map<String, String> args) throws IOException {
        Map<String, List<String>> multiValueHeaders = new HashedMap<>();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            multiValueHeaders.put(entry.getKey(), List.of(entry.getValue()));
        }
        HttpCallRequest request = new HttpCallRequest().httpMethod(httpMethod).urlTemplate(urlTemplate).bodyTemplate(bodyTemplate).headers(multiValueHeaders);
        return HttpUtils.httpCall(request, connectTimeoutSeconds, readTimeoutSeconds, args);
    }

    public static HttpCallResponse httpCall(HttpCallRequest request, int connectTimeoutSeconds, int readTimeoutSeconds, Map<String, String> args) throws IOException {
        String url = UrlUtils.fillUrlTemplate(request.getUrlTemplate(), args);
        String body = null;
        if (StringUtils.isNotBlank(request.getBodyTemplate())) {
            body = StringTemplateUtils.fillTemplate(request.getBodyTemplate(), args);
        }
        Map<String, List<String>> headers = new HashMap<>();
        if (MapUtils.isNotEmpty(request.getHeaders())) {
            for (Map.Entry<String, List<String>> entry : request.getHeaders().entrySet()) {
                for (String valueTemplate : entry.getValue()) {
                    String headerKey = StringTemplateUtils.fillTemplate(entry.getKey(), args);
                    String headerValue = StringTemplateUtils.fillTemplate(valueTemplate, args);
                    headers.put(headerKey, new ArrayList<>());
                    headers.get(headerKey).add(headerValue);
                }

            }
        }
        return HttpUtils.httpCall(request.getHttpMethod(), url, null == body ? null : body.getBytes(), headers, connectTimeoutSeconds, readTimeoutSeconds);
    }

    public static HttpCallResponse httpCall(String httpMethod, String url, byte[] body, Map<String, List<String>> headers, int connectTimeoutSeconds, int readTimeoutSeconds) throws IOException {
        if (StringUtils.isBlank(httpMethod)) {
            throw new IllegalStateException("empty http method not acceptable");
        }
        if (StringUtils.isBlank(url)) {
            throw new IllegalStateException("empty url not acceptable");
        }
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) URI.create(url).toURL().openConnection();
            // optional default is GET
            connection.setRequestMethod(httpMethod);
            connection.setConnectTimeout(connectTimeoutSeconds * 1000);
            connection.setReadTimeout(readTimeoutSeconds * 1000);
            if (MapUtils.isNotEmpty(headers)) {
                for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                    if (StringUtils.isBlank(entry.getKey())) {
                        throw new IllegalArgumentException("empty header name not acceptable: " + entry.getKey());
                    }
                    for (String value : entry.getValue()) {
                        if (StringUtils.isBlank(value)) {
                            throw new IllegalArgumentException("empty header value not acceptable: " + entry.getKey());
                        }
                        connection.setRequestProperty(entry.getKey(), value);
                    }
                }
            }
            if (null != body) {
                log.debug("setting request body, http method [{}] ", httpMethod);
                connection.setDoOutput(true);
                try (OutputStream outputStream = connection.getOutputStream()) {
                    outputStream.write(body);
                }
            }
            /*
                getResponseCode to hack to force HttpURLConnection to run the request
                Otherwise getErrorStream always returns null
             */
            int responseCode = connection.getResponseCode();
            InputStream errorStream = null;
            try {
                errorStream = connection.getErrorStream();
            } catch (Exception e) {
                log.trace("error reading error stream of calling url: [{}]", url, e);
            }

            InputStream responseStream = null;
            try {
                responseStream = connection.getInputStream();
            } catch (Exception e) {
                log.trace("error reading response stream of calling url: [{}]", url, e);
            }
            byte[] response = null == responseStream ? new byte[0] : IOUtils.toByteArray(responseStream);
            byte[] error = null == errorStream ? new byte[0] : IOUtils.toByteArray(errorStream);
            log.trace("http call[{}] response, code: [{}], details: [{}]", url, responseCode, IOUtils.toString(response, StandardCharsets.UTF_8.displayName()));
            HttpCallResponse callResponse = new HttpCallResponse(responseCode, response, error);
            callResponse.setHeaders(connection.getHeaderFields());
            callResponse.setUrl(url);
            return callResponse;
        } finally {
            if (null != connection) {
                connection.disconnect();
            }
        }
    }
}


