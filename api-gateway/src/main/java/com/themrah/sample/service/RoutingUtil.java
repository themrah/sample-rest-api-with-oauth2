package com.themrah.sample.service;

import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service
@Slf4j
public class RoutingUtil {
    @Value("${notAuthorizedUrl:http://localhost:8083/users}")
    private String notAuthorizedUrl;


    public void route(RequestContext requestContext) {
        route(requestContext, notAuthorizedUrl);
    }

    public void route(RequestContext requestContext, String url) {
        try {
            requestContext.setRouteHost(new URL(url));
        } catch (MalformedURLException e) {
            log.error("routing error", e);
        }
    }
}
