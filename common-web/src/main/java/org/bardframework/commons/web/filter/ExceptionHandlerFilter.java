package org.bardframework.commons.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.bardframework.commons.web.ExceptionControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;

import java.io.IOException;
import java.lang.reflect.Method;

public class ExceptionHandlerFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(ExceptionHandlerFilter.class);

    private final ObjectMapper objectMapper;
    private final ExceptionControllerAdvice exceptionControllerAdvice;
    private final ExceptionHandlerMethodResolver exceptionHandlerMethodResolver;

    public ExceptionHandlerFilter(ExceptionControllerAdvice exceptionControllerAdvice, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.exceptionControllerAdvice = exceptionControllerAdvice;
        this.exceptionHandlerMethodResolver = new ExceptionHandlerMethodResolver(exceptionControllerAdvice.getClass());
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException {
        try {
            chain.doFilter(req, res);
        } catch (Throwable ex) {
            if (ex instanceof ServletException && null != ex.getCause()) {
                this.handle((HttpServletResponse) res, ex.getCause());
            } else {
                this.handle((HttpServletResponse) res, ex);
            }
        }
    }

    public void handle(HttpServletResponse response, Throwable ex) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        Method method = exceptionHandlerMethodResolver.resolveMethodByExceptionType(ex.getClass());
        if (null == method) {
            log.error("{} exception occurred, but can't handle it using advices", ex.getClass(), ex);
            return;
        }
        try {
            Object resolverResponse = method.invoke(exceptionControllerAdvice, ex);
            objectMapper.writeValue(response.getWriter(), resolverResponse);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        } catch (Exception e) {
            log.error("error calling exception\n \nhandler method [{}] and setting result to response", method.getName(), e);
        }
    }
}
