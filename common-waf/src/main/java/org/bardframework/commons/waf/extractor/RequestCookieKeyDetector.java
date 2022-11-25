package org.bardframework.commons.waf.extractor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Objects;

public class RequestCookieKeyDetector implements RequestKeyDetector {

    private final String cookieName;

    public RequestCookieKeyDetector(String cookieName) {
        this.cookieName = cookieName;
    }

    @Override
    public String getUniqueKey(HttpServletRequest request, HttpServletResponse response) {
        if (null == request.getCookies()) {
            return null;
        }
        for (Cookie cookie : request.getCookies()) {
            if (Objects.equals(cookie.getName(), cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
