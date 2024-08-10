package org.bardframework.commons.waf.extractor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;

public class BearerTokenDetector implements RequestKeyDetector {

    @Override
    public String getUniqueKey(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        return null == token ? null : token.replaceFirst("Brarer ", "");
    }
}
