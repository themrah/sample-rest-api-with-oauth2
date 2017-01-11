package com.themrah.sample.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "themrah.security")
@Getter
@Setter
public class SecurityResourcesProperties {
    private List<String> secureResources;
}
