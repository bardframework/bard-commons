package org.bardframework.commons.waf;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bardframework.commons.waf.exception.CallLimitExceedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.List;

public class WafFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(WafFilter.class);

    private final List<RequestLimitChecker> checkers;

    public WafFilter(List<RequestLimitChecker> checkers) {
        this.checkers = checkers;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            for (RequestLimitChecker checker : checkers) {
                if (checker.match((HttpServletRequest) request)) {
                    checker.checkCallLimit((HttpServletRequest) request, (HttpServletResponse) response);
                }
            }
            chain.doFilter(request, response);
        } catch (CallLimitExceedException e) {
            log.warn("call limit exceed for [{}]", e.getKey());
            ((HttpServletResponse) response).setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        }
    }
}
