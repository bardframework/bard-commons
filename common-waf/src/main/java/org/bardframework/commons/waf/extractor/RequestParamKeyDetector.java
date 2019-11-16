package org.bardframework.commons.waf.extractor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestParamKeyDetector implements RequestKeyDetector {

    private final String paramName;

    public RequestParamKeyDetector(String paramName) {
        this.paramName = paramName;
    }

    @Override
    public String getUniqueKey(HttpServletRequest request, HttpServletResponse response) {
        return request.getParameter(paramName);
    }
}
