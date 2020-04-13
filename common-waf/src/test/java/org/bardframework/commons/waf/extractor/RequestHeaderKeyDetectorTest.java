package org.bardframework.commons.waf.extractor;

import org.assertj.core.api.Assertions;
import org.bardframework.commons.utils.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class RequestHeaderKeyDetectorTest {

    @Test
    void getUniqueKey() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        String headerName = RandomStringUtils.randomAlphabetic(5);
        request.addHeader(headerName, RandomStringUtils.randomAlphabetic(5));
        RequestHeaderKeyDetector headerKeyDetector = new RequestHeaderKeyDetector(headerName);
        Assertions.assertThat(headerKeyDetector.getUniqueKey(request, response)).isNotNull();
        Assertions.assertThat(headerKeyDetector.getUniqueKey(request, response)).isEqualTo(request.getHeader(headerName));
    }
}
