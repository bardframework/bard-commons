package org.bardframework.commons.security.filter;

import org.bardframework.commons.security.context.SecurityContextManager;
import org.bardframework.commons.security.token.AuthenticationAbstract;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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