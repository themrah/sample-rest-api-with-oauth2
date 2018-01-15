package com.themrah.sample.bff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ClientController {

    private static final String API_GATEWAY = "http://localhost:8081";
    private static final String RESOURCES_PATH = "/users/resources";
    private static final String EVENTS_PATH = "/events";
    private static final String SESSIONS_PATH = "/sessions";

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping({RESOURCES_PATH})
    public String resources(@RequestHeader String authorization) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        ResponseEntity<String> result = restTemplate.exchange(API_GATEWAY + RESOURCES_PATH, HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
        return result.getBody().toString();
    }

    @RequestMapping(EVENTS_PATH)
    public String events() {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<String> result = restTemplate.exchange(API_GATEWAY + EVENTS_PATH, HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
        return result.getBody().toString();
    }

    @RequestMapping(SESSIONS_PATH)
    public String sessions(@RequestHeader String authorization) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        ResponseEntity<String> result = restTemplate.exchange(API_GATEWAY + SESSIONS_PATH, HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
        return result.getBody().toString();
    }
}
