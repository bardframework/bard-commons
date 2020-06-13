package org.bardframework.commons.web.http;

import org.bardframework.commons.utils.IOUtils;
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

public abstract class HttpCallerAbstract {
    protected static final Logger LOGGER = LoggerFactory.getLogger(HttpCallerAbstract.class);

    protected HttpCallResult httpCall(Map<String, String> variables) throws IOException {
        HttpURLConnection connection = null;
        try {
            String url = this.fillUrl(this.getUrlTemplate(), variables);
            connection = (HttpURLConnection) new URL(url).openConnection();
            // optional default is GET
            connection.setRequestMethod(this.getHttpMethod());
            if (null != this.getContentType()) {
                connection.setRequestProperty("Content-Type", this.getContentType());
            }
            if (null != this.getBodyTemplate() && ("PUT".equalsIgnoreCase(this.getHttpMethod()) || "POST".equalsIgnoreCase(this.getHttpMethod()))) {
                LOGGER.debug("http method is [{}] setting request body", this.getHttpMethod());
                connection.setDoOutput(true);
                try (OutputStream outputStream = connection.getOutputStream()) {
                    outputStream.write(this.fillBody(this.getBodyTemplate(), variables).getBytes(StandardCharsets.UTF_8));
                }
            } else {
                LOGGER.debug("http method is not PUT or POST or request body is null, not set body");
            }
            InputStream stream = null == connection.getInputStream() ? connection.getErrorStream() : connection.getInputStream();
            String response = IOUtils.toString(stream, StandardCharsets.UTF_8);
            LOGGER.debug("http call[{}] response, code: [{}], details: [{}]", url, connection.getResponseCode(), response);
            return new HttpCallResult(connection.getResponseCode(), response, false);
        } finally {
            if (null != connection) {
                connection.disconnect();
            }
        }
    }

    protected String fillUrl(String urlTemplate, Map<String, String> variables) throws UnsupportedEncodingException {
        String result = String.valueOf(urlTemplate);
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            result = result.replaceAll(entry.getKey(), URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.displayName()));
        }
        return result;
    }

    protected String fillBody(String bodyTemplate, Map<String, String> variables) {
        String result = String.valueOf(bodyTemplate);
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            result = result.replaceAll(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public abstract String getHttpMethod();

    public abstract String getUrlTemplate();

    public abstract String getBodyTemplate();

    public abstract String getContentType();

}
