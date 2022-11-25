package org.bardframework.commons.security.context;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bardframework.commons.security.token.AuthenticationAbstract;

public interface SecurityContextManager<A extends AuthenticationAbstract<U>, U> {

    void setSecurityContext(HttpServletRequest request, HttpServletResponse response);

    void clearSecurityContext(HttpServletRequest request, HttpServletResponse response);

    String putAuthentication(A authentication, HttpServletResponse response);

    A getAuthentication(HttpServletRequest request);

    U getUser(HttpServletRequest request);

    void setTokenName(String tokenName);

    void setToken(HttpServletResponse response, String tokenId);
}