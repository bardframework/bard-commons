package org.bardframework.commons.web.cors;

import org.bardframework.commons.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CorsFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CorsFilter.class);

    private final List<RequestMatcher> corsRequestMatchers;
    private final AntPathMatcher antPathMatcher;
    private List<String> allowedOrigins;
    private List<String> allowedMethods;
    private List<String> allowedHeaders;
    private boolean allowedCredentials;
    private List<String> exposedHeaders;
    private int maxAge;
    private boolean enable;

    public CorsFilter(List<String> corsMapping) {
        this.corsRequestMatchers = new ArrayList<>();
        for (String url : corsMapping) {
            this.corsRequestMatchers.add(new AntPathRequestMatcher(url));
        }
        this.antPathMatcher = new AntPathMatcher();
        this.antPathMatcher.setTrimTokens(false);
        this.antPathMatcher.setCaseSensitive(false);
    }

    protected CorsFilter(List<String> corsMapping, List<String> allowedOrigins, List<String> allowedMethods, List<String> allowedHeaders, List<String> exposedHeaders, boolean allowedCredentials, int maxAge, boolean enable) {
        this(corsMapping);
        this.allowedOrigins = allowedOrigins;
        this.allowedMethods = allowedMethods;
        this.allowedHeaders = allowedHeaders;
        this.allowedCredentials = allowedCredentials;
        this.exposedHeaders = exposedHeaders;
        this.maxAge = maxAge;
        this.enable = enable;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String origin = request.getHeader("Origin");
        if (StringUtils.isBlank(origin) || !this.needCorsCheck(request)) {
            chain.doFilter(request, response);
            return;
        }
        if (!this.isEnable()) {
            LOGGER.debug("cors filter is disable and not processed for request [{}], check config [{}]", request.getRequestURI(), CorsConfig.CORS_ENABLE.getKey());
            return;
        }
        if (this.isAllowedOrigin(origin)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
        }
        if (null != this.getAllowedMethods()) {
            response.setHeader("Access-Control-Allow-Methods", String.join(",", this.getAllowedMethods()));
        }
        if (0 < this.getMaxAge()) {
            response.setHeader("Access-Control-Max-Age", String.valueOf(this.getMaxAge()));
        }
        if (null != this.getAllowedHeaders()) {
            response.setHeader("Access-Control-Allow-Headers", String.join(",", this.getAllowedHeaders()));
        }
        if (this.isAllowedCredentials()) {
            response.setHeader("Access-Control-Allow-Credentials", "true");
        }
        if (null != this.getExposedHeaders()) {
            response.addHeader("Access-Control-Expose-Headers", String.join(",", this.getExposedHeaders()));
        }
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean needCorsCheck(HttpServletRequest request) {
        return this.getCorsRequestMatchers().stream().anyMatch(corsRequestMatcher -> corsRequestMatcher.matches(request));
    }

    private boolean isAllowedOrigin(String origin) {
        return this.getAllowedOrigins().stream().anyMatch(allowedOrigin -> this.getAntPathMatcher().match(allowedOrigin, origin));
    }

    public List<RequestMatcher> getCorsRequestMatchers() {
        return corsRequestMatchers;
    }

    protected boolean isEnable() {
        return enable;
    }

    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }

    public List<String> getAllowedMethods() {
        return allowedMethods;
    }

    public List<String> getAllowedHeaders() {
        return allowedHeaders;
    }

    public boolean isAllowedCredentials() {
        return allowedCredentials;
    }

    public List<String> getExposedHeaders() {
        return exposedHeaders;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public AntPathMatcher getAntPathMatcher() {
        return antPathMatcher;
    }
}
