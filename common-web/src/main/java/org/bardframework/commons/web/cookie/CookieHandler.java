package org.bardframework.commons.web.cookie;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.util.CookieGenerator;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class CookieHandler extends CookieGenerator {

    @Nullable
    private String cookieSameSite;

    public Cookie get(HttpServletRequest request) {
        return WebUtils.getCookie(request, Objects.requireNonNull(this.getCookieName()));
    }

    public String getValue(HttpServletRequest request) {
        Cookie cookie = this.get(request);
        return null == cookie ? null : cookie.getValue();
    }

    /**
     * don't set domain when it is empty
     */
    @Override
    protected Cookie createCookie(String cookieValue) {
        Cookie cookie = new Cookie(Objects.requireNonNull(this.getCookieName()), cookieValue);
        /*
            origin implementation set domain when it is empty (not null)
         */
        if (StringUtils.isNotBlank(this.getCookieDomain())) {
            cookie.setDomain(getCookieDomain());
        }
        cookie.setPath(this.getCookiePath());
        return cookie;
    }

    @Override
    public void addCookie(HttpServletResponse response, String cookieValue) {
        this.addCookie(response, cookieValue, this.getCookieMaxAge());
    }

    /**
     * Add a cookie with the given value and age to the response,
     * using the cookie descriptor settings of this generator.
     *
     * @param response    the HTTP response to add the cookie to
     * @param cookieValue the value of the cookie to add
     * @param maxAge      max age value of the cookie to add
     */
    public void addCookie(HttpServletResponse response, String cookieValue, Integer maxAge) {
        StringBuilder setCookieHeader = new StringBuilder(String.format("%s=%s;", this.getCookieName(), cookieValue));
        if (null != maxAge) {
            setCookieHeader.append(String.format("Max-Age=%d;", maxAge));
        }
        if (this.isCookieSecure()) {
            setCookieHeader.append("Secure;");
        }
        if (this.isCookieHttpOnly()) {
            setCookieHeader.append("HttpOnly;");
        }
        if (StringUtils.isNotBlank(this.getCookieDomain())) {
            setCookieHeader.append(String.format("Domain=%s;", this.getCookieDomain()));
        }
        if (StringUtils.isNotBlank(this.getCookiePath())) {
            setCookieHeader.append(String.format("Path=%s;", this.getCookiePath()));
        }
        if (StringUtils.isNotBlank(this.getCookieSameSite())) {
            setCookieHeader.append(String.format("SameSite=%s;", this.getCookieSameSite()));
        }
        response.addHeader("Set-Cookie", setCookieHeader.toString());
    }

    @Nullable
    public String getCookieSameSite() {
        return cookieSameSite;
    }

    public void setCookieSameSite(@Nullable String cookieSameSite) {
        this.cookieSameSite = cookieSameSite;
    }
}
