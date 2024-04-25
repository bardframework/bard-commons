package org.bardframework.commons.utils.http;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class HttpCallRequest {

    private String httpMethod;
    private String urlTemplate;
    private String bodyTemplate;
    private Map<String, List<String>> headers = new HashMap<>();

    public String getHeader(final String name) {
        return headers.get(name).get(0);
    }


    public HttpCallRequest httpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public HttpCallRequest urlTemplate(String urlTemplate) {
        this.urlTemplate = urlTemplate;
        return this;
    }

    public HttpCallRequest bodyTemplate(String bodyTemplate) {
        this.bodyTemplate = bodyTemplate;
        return this;
    }

    public HttpCallRequest headers(String name, List<String> values) {
        this.headers.putIfAbsent(name, new ArrayList<>());
        this.headers.get(name).addAll(values);
        return this;
    }

    public HttpCallRequest header(String name, String value) {
        this.headers.putIfAbsent(name, new ArrayList<>());
        this.headers.get(name).add(value);
        return this;
    }

    public HttpCallRequest headers(Map<String, List<String>> headers) {
        this.headers = headers;
        return this;
    }
}
