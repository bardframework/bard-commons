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
        if (StringUtils.isBlank(request.getHttpMethod())) {
            throw new IllegalStateException("empty http method not acceptable");
        }
        if (StringUtils.isBlank(request.getUrlTemplate())) {
            throw new IllegalStateException("empty url not acceptable");
        }
        HttpURLConnection connection = null;
        try {
            String url = UrlUtils.fillUrlTemplate(request.getUrlTemplate(), args);
            connection = (HttpURLConnection) URI.create(url).toURL().openConnection();
            // optional default is GET
            connection.setRequestMethod(request.getHttpMethod());
            connection.setConnectTimeout(connectTimeoutSeconds * 1000);
            connection.setReadTimeout(readTimeoutSeconds * 1000);
            if (MapUtils.isNotEmpty(request.getHeaders())) {
                for (Map.Entry<String, List<String>> entry : request.getHeaders().entrySet()) {
                    for (String valueTemplate : entry.getValue()) {
                        if (null == valueTemplate) {
                            log.debug("value of header [{}] is null, ignoring it", entry.getKey());
                            continue;
                        }
                        String headerValue = StringTemplateUtils.fillTemplate(valueTemplate, args);
                        connection.setRequestProperty(entry.getKey(), headerValue);
                    }

                }
            }
            if (StringUtils.isNotBlank(request.getBodyTemplate())) {
                log.debug("setting request body, http method [{}] ", request.getHttpMethod());
                connection.setDoOutput(true);
                String body = StringTemplateUtils.fillTemplate(request.getBodyTemplate(), args);
                try (OutputStream outputStream = connection.getOutputStream()) {
                    outputStream.write(body.getBytes(StandardCharsets.UTF_8));
                }
            }
            /*
                getResponseCode to hack to force HttpURLConnection to run the request
                Otherwise getErrorStream always returns null
             */
            int responseCode = connection.getResponseCode();
            InputStream errorStream = connection.getErrorStream();
            InputStream responseStream = connection.getInputStream();
            byte[] response = null == responseStream ? new byte[0] : IOUtils.toByteArray(responseStream);
            byte[] error = null == errorStream ? new byte[0] : IOUtils.toByteArray(errorStream);
            log.trace("http call[{}] response, code: [{}], details: [{}]", url, responseCode, IOUtils.toString(response, StandardCharsets.UTF_8.displayName()));
            return new HttpCallResponse(responseCode, response, error, connection.getHeaderFields());
        } finally {
            if (null != connection) {
                connection.disconnect();
            }
        }
    }
}


