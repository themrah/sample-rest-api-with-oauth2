package com.themrah.sample.resourceServer.configuration;

import com.themrah.sample.resourceServer.configuration.filter.ResourceFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Value("http://localhost:8080/uaa/check_token")
    private String checkTokenUrl;

    @Bean
    public RestTemplate restTemplate() {
        final RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    @Bean
    public ResourceFilter resourceFilter(RestTemplate restTemplate){
        ResourceFilter resourceFilter = new ResourceFilter(restTemplate,checkTokenUrl);
        return resourceFilter;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean(ResourceFilter resourceFilter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(resourceFilter);
        registrationBean.addUrlPatterns("/resources");
        return registrationBean;
    }


}
