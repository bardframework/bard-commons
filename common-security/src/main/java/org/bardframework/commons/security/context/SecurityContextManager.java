package org.bardframework.commons.security.context;

import org.bardframework.commons.security.token.AuthenticationAbstract;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SecurityContextManager<A extends AuthenticationAbstract<U>, U> {

    void setSecurityContext(HttpServletRequest request, HttpServletResponse response);

    void clearSecurityContext(HttpServletRequest request, HttpServletResponse response);

    String putAuthentication(A authentication, HttpServletResponse response);

    A getAuthentication(HttpServletRequest request);

    U getUser(HttpServletRequest request);

    void setTokenName(String tokenName);

    void setToken(HttpServletResponse response, String tokenId);
}