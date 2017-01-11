package com.themrah.sample;

import com.themrah.sample.filter.SecurityPostFilter;
import com.themrah.sample.filter.SecurityPreFilter;
import com.netflix.zuul.ZuulFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public ZuulFilter securityPreFilter() {
        return new SecurityPreFilter();
    }

    @Bean
    public ZuulFilter securityPostFilter() {
        return new SecurityPostFilter();
    }
}
