package org.bardframework.commons.utils.http;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
public class HttpCallResponse {

    private final int statusCode;
    private final byte[] body;
    private final byte[] error;
    private Map<String, List<String>> headers = new HashMap<>();
    private String url;

    public String getHeader(final String name) {
        return headers.get(name).get(0);
    }

    public String getContentType() {
        return headers.get("Content-Type").get(0);
    }

    public String getBodyAsString() {
        return null == body ? null : new String(body);
    }

    public String getErrorAsString() {
        return null == error ? null : new String(error);
    }

    @Override
    public String toString() {
        return "HttpCallResponse{" +
                "url=" + url +
                ", statusCode=" + statusCode +
                ", body=" + (null == body ? null : new String(body)) +
                ", error=" + (null == error ? null : new String(error)) +
                ", headers=" + headers +
                '}';
    }
}
