package org.bardframework.commons.waf.extractor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
