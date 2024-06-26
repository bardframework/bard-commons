package org.bardframework.commons.web.cors;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Getter
public class CorsFilter implements Filter {

    private final RequestMatcher corsRequestMatcher;
    private final AntPathMatcher antPathMatcher;
    private List<String> allowedOrigins;
    private List<String> allowedMethods;
    private List<String> allowedHeaders;
    private boolean allowedCredentials;
    private List<String> exposedHeaders;
    private int maxAge;

    public CorsFilter(List<String> corsMapping) {
        this.corsRequestMatcher = new OrRequestMatcher(corsMapping.stream().map(AntPathRequestMatcher::new).collect(Collectors.toList()));
        this.antPathMatcher = new AntPathMatcher();
        this.antPathMatcher.setTrimTokens(false);
        this.antPathMatcher.setCaseSensitive(false);
    }

    public CorsFilter(List<String> corsMapping, List<String> allowedOrigins, List<String> allowedMethods, List<String> allowedHeaders, List<String> exposedHeaders, boolean allowedCredentials, int maxAge) {
        this(corsMapping);
        this.allowedOrigins = allowedOrigins;
        this.allowedMethods = allowedMethods;
        this.allowedHeaders = allowedHeaders;
        this.allowedCredentials = allowedCredentials;
        this.exposedHeaders = exposedHeaders;
        this.maxAge = maxAge;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String origin = request.getHeader(HttpHeaders.ORIGIN);
        if (StringUtils.isBlank(origin) || !this.getCorsRequestMatcher().matches(request)) {
            chain.doFilter(request, response);
            return;
        }
        if (this.isAllowedOrigin(origin)) {
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
        }
        if (null != this.getAllowedMethods()) {
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, String.join(",", this.getAllowedMethods()));
        }
        if (0 < this.getMaxAge()) {
            response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, String.valueOf(this.getMaxAge()));
        }
        if (null != this.getAllowedHeaders()) {
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, String.join(",", this.getAllowedHeaders()));
        }
        if (this.isAllowedCredentials()) {
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        }
        if (null != this.getExposedHeaders()) {
            response.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, String.join(",", this.getExposedHeaders()));
        }
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean isAllowedOrigin(String origin) {
        return this.getAllowedOrigins().stream().anyMatch(allowedOrigin -> this.getAntPathMatcher().match(allowedOrigin, origin));
    }
}
