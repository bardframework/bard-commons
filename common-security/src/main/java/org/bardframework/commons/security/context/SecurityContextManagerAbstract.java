package org.bardframework.commons.security.context;

import org.apache.commons.lang3.StringUtils;
import org.bardframework.commons.security.token.AuthenticationAbstract;
import org.bardframework.commons.security.token.manager.TokenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class SecurityContextManagerAbstract<A extends AuthenticationAbstract<U>, U> implements SecurityContextManager<A, U> {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
    protected final TokenManager<A> tokenManager;
    protected String tokenName = "token";

    protected SecurityContextManagerAbstract(TokenManager<A> tokenManager) {
        this.tokenManager = tokenManager;
    }

    /**
     * used in security context filter
     */
    @Override
    public void setSecurityContext(HttpServletRequest request, HttpServletResponse response) {
        String tokenId = this.getToken(request);
        if (StringUtils.isBlank(tokenId)) {
            SecurityContextHolder.clearContext();
            return;
        }
        A token = this.tokenManager.get(tokenId);
        if (null == token) {
            this.LOGGER.info("detect invalid token, destroying it. '{}'", tokenId);
            this.deleteTicket(request, response);
            SecurityContextHolder.clearContext();
        } else {
            SecurityContextHolder.getContext().setAuthentication(token);
        }
    }

    @Override
    public void clearSecurityContext(HttpServletRequest request, HttpServletResponse response) {
        String token = this.getToken(request);
        if (null != token) {
            this.tokenManager.remove(token);
            this.deleteTicket(request, response);
            SecurityContextHolder.clearContext();
        }
    }

    /**
     * used in login filter
     */
    @Override
    public String putAuthentication(A authentication, HttpServletResponse response) {
        String tokenId = tokenManager.put(authentication);
        this.setToken(response, tokenId);
        return tokenId;
    }

    @Override
    public A getAuthentication(HttpServletRequest request) {
        String token = this.getToken(request);
        if (null == token) {
            return null;
        }
        return tokenManager.get(token);
    }

    @Override
    public U getUser(HttpServletRequest request) {
        String tokenId = this.getToken(request);
        if (null == tokenId) {
            return null;
        }
        A token = tokenManager.get(tokenId);
        return null == token ? null : token.getUser();
    }

    @Override
    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    protected abstract String getToken(HttpServletRequest request);

    protected abstract void deleteTicket(HttpServletRequest request, HttpServletResponse response);
}
