package org.bardframework.commons.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import org.assertj.core.api.Assertions;
import org.bardframework.commons.web.BaseExceptionControllerAdvice;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

class ExceptionHandlerFilterTest {

    @Test
    void doFilter() {
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        MockHttpServletResponse servletResponse = new MockHttpServletResponse();
        FilterChain filterChain = new MockFilterChain();
        BaseExceptionControllerAdvice exceptionControllerAdvice = () -> null;
        ExceptionHandlerFilter filter = new ExceptionHandlerFilter(exceptionControllerAdvice, new ObjectMapper());
        try {
            filter.doFilter(servletRequest, servletResponse, filterChain);
            Assertions.assertThat(servletResponse).isNotNull();
        } catch (IOException e) {
            fail();
        }
    }
}