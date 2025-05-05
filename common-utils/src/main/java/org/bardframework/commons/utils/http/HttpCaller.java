package org.bardframework.commons.utils.http;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
@Setter
public class HttpCaller {

    protected String httpMethod;
    protected String urlTemplate;
    protected String bodyTemplate;
    protected int connectTimeoutSeconds = 30;
    protected int readTimeoutSeconds = 3600;
    protected boolean logResponse;
    protected boolean disable;
    protected Map<String, String> headers = new HashMap<>();

    public HttpCaller() {
    }

    public HttpCaller(String httpMethod, String urlTemplate) {
        this.httpMethod = httpMethod;
        this.urlTemplate = urlTemplate;
    }

    public HttpCallResponse call(Map<String, Object> args) throws IOException {
        return this.call(this.getHeaders(), args);
    }

    /**
     * call with custom headers
     */
    public HttpCallResponse call(Map<String, String> headers, Map<String, Object> args) throws IOException {
        if (this.isDisable()) {
            log.error("[{}] is disable.", this.getClass().getSimpleName());
            return new HttpCallResponse(-1, new byte[0], new byte[0]);
        }
        Map<String, String> allArgs = new HashMap<>();
        args.forEach((key, value) -> allArgs.put(key, null == value ? null : value.toString()));
        this.getDefaultArgs().forEach((key, value) -> allArgs.put(key, value.toString()));
        HttpCallResponse callResult = HttpUtils.httpCall(this.getHttpMethod(), this.getUrlTemplate(), this.getBodyTemplate(), this.getConnectTimeoutSeconds(), this.getReadTimeoutSeconds(), headers, allArgs);
        if (this.isLogResponse()) {
            log.info("calling url[{}], response code: [{}], response body: [{}]", this.getUrlTemplate(), callResult.getStatusCode(), new String(callResult.getBody(), StandardCharsets.UTF_8));
        }
        return callResult;
    }

    protected Map<String, Object> getDefaultArgs() {
        return Map.of();
    }
}
