package org.bardframework.commons.waf.extractor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
