package org.bardframework.commons.utils.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class HttpCallResponse {

    private final int statusCode;
    private final byte[] body;
    private final byte[] error;
    private Map<String, List<String>> headers = new HashMap<>();

    public String getHeader(final String name) {
        return headers.get(name).get(0);
    }

    public String getContentType() {
        return headers.get("Content-Type").get(0);
    }

    public String getBodyAsString() {
        return null == error ? null : new String(error);
    }

    public String getErrorAsString() {
        return null == error ? null : new String(error);
    }
}
