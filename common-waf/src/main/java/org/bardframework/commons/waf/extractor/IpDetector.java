package org.bardframework.commons.waf.extractor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class IpDetector implements RequestKeyDetector {

    private final List<String> headerIpCandidates;

    public IpDetector() {
        this.headerIpCandidates = Arrays.asList(
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_X_FORWARDED_FOR",
                "HTTP_X_FORWARDED",
                "HTTP_X_CLUSTER_CLIENT_IP",
                "HTTP_CLIENT_IP",
                "HTTP_FORWARDED_FOR",
                "HTTP_FORWARDED",
                "HTTP_VIA",
                "REMOTE_ADDR"
        );
    }

    public IpDetector(List<String> headerIpCandidates) {
        this.headerIpCandidates = headerIpCandidates;
    }

    @Override
    public String getUniqueKey(HttpServletRequest request, HttpServletResponse response) {
        return this.detect(request);
    }

    public String detect(HttpServletRequest request) {
        for (String header : headerIpCandidates) {
            String headerValue = request.getHeader(header);
            if (StringUtils.isNotEmpty(headerValue) && !"unknown".equalsIgnoreCase(headerValue)) {
                return headerValue.split(",")[0];
            }
        }
        return request.getRemoteAddr();
    }
}
