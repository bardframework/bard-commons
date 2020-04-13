package org.bardframework.commons.web.cookie;

import org.bardframework.commons.utils.StringUtils;
import org.springframework.web.util.CookieGenerator;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieHandler extends CookieGenerator {

    public Cookie get(HttpServletRequest request) {
        return WebUtils.getCookie(request, this.getCookieName());
    }

    public String getValue(HttpServletRequest request) {
        Cookie cookie = this.get(request);
        return null == cookie ? null : cookie.getValue();
    }

    /**
     * don't set domain when it is empty
     *
     * @param cookieValue
     * @return
     */
    @Override
    protected Cookie createCookie(String cookieValue) {
        Cookie cookie = new Cookie(this.getCookieName(), cookieValue);
        /*
            origin implementation set domain when it is empty (not null)
         */
        if (StringUtils.isNotBlank(this.getCookieDomain())) {
            cookie.setDomain(getCookieDomain());
        }
        cookie.setPath(this.getCookiePath());
        return cookie;
    }

    /**
     * Add a cookie with the given value & age to the response,
     * using the cookie descriptor settings of this generator.
     * <p>Delegates to {@link #createCookie} for cookie creation.
     *
     * @param response    the HTTP response to add the cookie to
     * @param cookieValue the value of the cookie to add
     * @param maxAge      max age value of the cookie to add
     */
    public void addCookie(HttpServletResponse response, String cookieValue, Integer maxAge) {
        Cookie cookie = this.createCookie(cookieValue);
        if (null != maxAge) {
            cookie.setMaxAge(maxAge);
        }
        if (isCookieSecure()) {
            cookie.setSecure(true);
        }
        if (isCookieHttpOnly()) {
            cookie.setHttpOnly(true);
        }
        response.addCookie(cookie);
    }
}
