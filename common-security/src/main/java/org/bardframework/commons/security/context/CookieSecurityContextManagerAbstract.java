package org.bardframework.commons.security.context;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bardframework.commons.security.token.AuthenticationAbstract;
import org.bardframework.commons.security.token.manager.TokenManager;

public abstract class CookieSecurityContextManagerAbstract<A extends AuthenticationAbstract<U>, U> extends SecurityContextManagerAbstract<A, U> {

    public CookieSecurityContextManagerAbstract(TokenManager<A> tokenManager) {
        super(tokenManager);
    }

    @Override
    public void setToken(HttpServletResponse response, String tokenId) {
        response.addCookie(new Cookie(tokenName, tokenId));
    }

    @Override
    protected String getToken(HttpServletRequest request) {
        if (null == request.getCookies()) {
            return null;
        }
        for (Cookie cookie : request.getCookies()) {
            if (tokenName.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    @Override
    public void deleteTicket(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(tokenName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}