package org.bardframework.commons.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.lang.reflect.Type;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Vahid Zafari on 4/23/2017.
 */
public interface WebTestHelper {

    Logger LOGGER = LoggerFactory.getLogger(WebTestHelper.class);

    MockMvc getMockMvc();

    ObjectMapper getObjectMapper();

    default void preExecute(MockHttpServletRequestBuilder request) {

    }

    default <T> T execute(MockHttpServletRequestBuilder request, HttpStatus expectedStatus, Class<T> returnType)
            throws Exception {
        return this.execute(request, expectedStatus, new TypeReference<T>() {
            @Override
            public Type getType() {
                return returnType;
            }
        });
    }

    default <T> T execute(MockHttpServletRequestBuilder request, HttpStatus expectedStatus, TypeReference<T> returnType)
            throws Exception {
        request.accept(MediaType.APPLICATION_JSON);
        MvcResult result = this.execute(request, expectedStatus);
        if (StringUtils.isNotBlank(result.getResponse().getContentAsString())) {
            return this.getObjectMapper().readValue(result.getResponse().getContentAsString(), returnType);
        } else {
            return null;
        }
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
        LOGGER.info("call details:\nurl: {} {}\nstatus: {}\nrequest:\n{}\nresponse:\n{}\n", result.getRequest().getMethod(), result.getRequest().getRequestURI(), result.getResponse().getStatus(), result.getRequest().getContentAsString(), result.getResponse().getContentAsString());
        return result;
    }
}
