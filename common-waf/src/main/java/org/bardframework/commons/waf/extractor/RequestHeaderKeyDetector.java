package org.bardframework.commons.waf.extractor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RequestHeaderKeyDetector implements RequestKeyDetector {

    private final String headerName;

    public RequestHeaderKeyDetector(String headerName) {
        this.headerName = headerName;
    }

    @Override
    public String getUniqueKey(HttpServletRequest request, HttpServletResponse response) {
        return request.getHeader(headerName);
    }
}
