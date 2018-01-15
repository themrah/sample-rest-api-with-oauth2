package com.themrah.sample.resourceServer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class ResourceController {

    @Autowired
    RestTemplate restTemplate;

    //TODO
    private static final String RESOURCES_PATH = "/resources";

    @RequestMapping(value = RESOURCES_PATH, method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS})
    @ResponseStatus(HttpStatus.OK)
    public void resources(HttpServletRequest httpRequest) {

    }
}
