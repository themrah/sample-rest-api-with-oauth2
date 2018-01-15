package com.themrah.sample.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ResourceController {

    @RequestMapping("/resources")
    public String resources(@RequestHeader(value = "X-TraceId",required = false) String traceId) {
        log.info("X-TraceId: " + traceId);
        return "user resources";
    }

    @RequestMapping("/events")
    public String events() {
        return "EventList ...";
    }

    @RequestMapping({"/admin"})
    public String admin() {
        return "Admin";
    }


    @RequestMapping({"/login"})
    public String login() {
        return "Login olmanız gerekmektedir. Username: Password:";
    }

    @RequestMapping({"/hata"})
    public String error() {
        return "Error!!!";
    }
}
