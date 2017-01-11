package com.themrah.sample.resourceServer.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "themrah.scopes")
@Getter
@Setter
@Component
public class ScopeProperties {
    private List<String> resources;
}
