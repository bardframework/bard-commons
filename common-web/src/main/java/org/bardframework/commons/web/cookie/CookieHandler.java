package org.bardframework.commons.web.cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.WebUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Slf4j
public class CookieHandler {

    private final String name;
    private final String path;
    private String domain;
    private Integer maxAge;
    private Boolean secure;
    private Boolean httpOnly;
    private Boolean hostOnly;
    private String sameSite;

    /**
     * Default path that cookies will be visible to: "/", i.e. the entire server.
     */
    public CookieHandler(String name) {
        this(name, "/");
    }

    public CookieHandler(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public Cookie get(HttpServletRequest request) {
        return WebUtils.getCookie(request, Objects.requireNonNull(this.getName()));
    }

    public String getValue(HttpServletRequest request) {
        Cookie cookie = this.get(request);
        return null == cookie ? null : cookie.getValue();
    }

    public void addCookie(HttpServletResponse response, String cookieValue) {
        this.addCookie(response, cookieValue, this.getMaxAge());
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
        List<String> attributes = this.constructAttributes(cookieValue, maxAge);
        response.addHeader("Set-Cookie", String.join(";", attributes));
    }

    /**
     * Remove the cookie that this generator describes from the response.
     * Will generate a cookie with empty value and max age 0.
     *
     * @param response the HTTP response to remove the cookie from
     */
    public void removeCookie(HttpServletResponse response) {
        List<String> attributes = this.constructAttributes("", 0);
        attributes.add("expires=Thu, Jan 01 1970 00:00:00 UTC");
        response.addHeader("Set-Cookie", String.join(";", attributes));
    }

    protected List<String> constructAttributes(String cookieValue, Integer maxAge) {
        List<String> attributes = new ArrayList<>();
        attributes.add(this.getName() + "=" + cookieValue);
        if (null != maxAge) {
            attributes.add("Max-Age=" + maxAge);
        }
        if (Boolean.TRUE.equals(this.getSecure())) {
            attributes.add("Secure");
        }
        if (Boolean.TRUE.equals(this.getHttpOnly())) {
            attributes.add("HttpOnly");
        }
        if (Boolean.TRUE.equals(this.getHostOnly())) {
            attributes.add("HostOnly");
        }
        if (StringUtils.isNotBlank(this.getDomain())) {
            attributes.add("Domain=" + this.getDomain());
        }
        if (StringUtils.isNotBlank(this.getPath())) {
            attributes.add("Path=" + this.getPath());
        }
        if (StringUtils.isNotBlank(this.getSameSite())) {
            attributes.add("SameSite=" + this.getSameSite());
        }
        return attributes;
    }

}
