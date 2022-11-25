package org.bardframework.commons.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bardframework.commons.security.context.SecurityContextManager;
import org.bardframework.commons.security.token.AuthenticationAbstract;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by vahid (va.zafari@gmail.com) on 12/19/17.
 */
public abstract class SecurityContextFilter<T extends AuthenticationAbstract<?>> implements Filter {

    private final SecurityContextManager<T, ?> securityContextManager;

    public SecurityContextFilter(@Autowired SecurityContextManager<T, ?> securityContextManager) {
        this.securityContextManager = securityContextManager;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        securityContextManager.setSecurityContext((HttpServletRequest) request, (HttpServletResponse) response);
        chain.doFilter(request, response);
    }
}