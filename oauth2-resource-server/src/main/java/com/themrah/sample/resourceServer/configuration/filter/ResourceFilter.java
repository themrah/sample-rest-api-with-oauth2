package com.themrah.sample.resourceServer.configuration.filter;

import com.themrah.sample.resourceServer.configuration.ScopeProperties;
import com.themrah.sample.resourceServer.model.response.CheckTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class ResourceFilter implements Filter {

    @Autowired
    private ScopeProperties scopeProperties;

    private final RestTemplate restTemplate;
    private final String checkTokenUrl;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String traceId = request.getHeader("X-TraceId");
        log.info("X-TraceId: " + traceId);
        String redirectUrl = request.getHeader("X-RedirectURL");
        String accessToken = request.getHeader("Authorization");


        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (StringUtils.isEmpty(accessToken)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        String token = accessToken.replaceAll("Bearer", "").trim();

        if (isAuthorizedTokenForRequest(token)) {
            response.setStatus(HttpServletResponse.SC_FOUND);
            response.sendRedirect(redirectUrl);
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
    }

    private boolean isAuthorizedTokenForRequest(String token) throws UnsupportedEncodingException {
        Map<String, Object> checkTokenMap = new HashMap<String, Object>();
        checkTokenMap.put("token", token);
        CheckTokenResponse checkTokenResponse;
        try {
            checkTokenResponse = checkToken(checkTokenUrl, checkTokenMap);
        } catch (Exception e) {
            return false;
        }
        List<String> scopes = checkTokenResponse.getScope();
        boolean isAuthorized = false;
        for (String scope : scopes) {
            if (scopeProperties.getResources()
                    .stream()
                    .anyMatch(eachItem -> scope.contains(eachItem))) {
                isAuthorized = true;
                break;
            }
        }
        return isAuthorized;
    }

    private CheckTokenResponse checkToken(String checkTokenUrl, Map<String, Object> params) throws UnsupportedEncodingException {

        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("app", "appclientsecret"));
        HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        restTemplate.setRequestFactory(requestFactory);

        checkTokenUrl = checkTokenUrl + "?token=" + params.get("token");
        ResponseEntity<CheckTokenResponse> result = restTemplate.exchange(checkTokenUrl, HttpMethod.GET, null, CheckTokenResponse.class);
        return result.getBody();
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}