package com.themrah.sample.filter;

import com.themrah.sample.configuration.SecurityResourcesProperties;
import com.themrah.sample.service.RoutingUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class SecurityPreFilter extends ZuulFilter {
    @Autowired
    private SecurityResourcesProperties securityResourcesProperties;
    @Autowired
    private RoutingUtil routingUtil;

    @Value("${resourceServer.url:http://localhost:8084/resources}")
    private String resourceServerUrl;

    private static Integer traceId = 0;

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return isAuthorizationRequired(ctx.getRequest());
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader("X-TraceId", SecurityPreFilter.increaseTraceId().toString());
        log.info("Request started with X-TraceId: " + traceId);
        ctx.addZuulRequestHeader("X-RedirectURL", ctx.getRouteHost().toString());
        routingUtil.route(ctx, resourceServerUrl);
        return null;
    }

    private boolean isAuthorizationRequired(HttpServletRequest request) {
        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
        return securityResourcesProperties.getSecureResources()
                .stream()
                .anyMatch(eachItem -> request.getRequestURL().toString().contains(eachItem));
    }

    private static Integer increaseTraceId() {
        return ++traceId;
    }
}
