package org.bardframework.commons.web;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Vahid Zafari on 4/23/2017.
 */
public interface WebTestHelper {

    Logger log = LoggerFactory.getLogger(WebTestHelper.class);

    MockMvc getMockMvc();

    ObjectMapper getObjectMapper();

    default void preExecute(MockHttpServletRequestBuilder request) {

    }

    default <T> T execute(MockHttpServletRequestBuilder request, HttpStatus expectedStatus, Class<T> returnType)
            throws Exception {
        return this.execute(request, expectedStatus, this.getObjectMapper().getTypeFactory().constructType(returnType));
    }

    default <T> T execute(MockHttpServletRequestBuilder request, HttpStatus expectedStatus, JavaType returnType)
            throws Exception {
        request.accept(MediaType.APPLICATION_JSON);
        MvcResult result = this.execute(request, expectedStatus);
        if (StringUtils.isBlank(result.getResponse().getContentAsString())) {
            return null;
        }
        return this.getObjectMapper().readValue(result.getResponse().getContentAsString(), returnType);
    }

    default MvcResult execute(MockHttpServletRequestBuilder request, HttpStatus expectedStatus)
            throws Exception {
        MvcResult result = this.execute(request);
        assertThat(result.getResponse().getStatus()).isEqualTo(expectedStatus.value());
        return result;
    }

    default MvcResult execute(MockHttpServletRequestBuilder request)
            throws Exception {
        this.preExecute(request);
        MvcResult result = this.getMockMvc().perform(request).andReturn();
        log.info("call details:\nurl: {} {}\nstatus: {}\nrequest:\n{}\nresponse:\n{}\n", result.getRequest().getMethod(), result.getRequest().getRequestURI(), result.getResponse().getStatus(), result.getRequest().getContentAsString(), result.getResponse().getContentAsString());
        return result;
    }
}
