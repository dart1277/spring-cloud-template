package com.cx.loadbalanced.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties("spring.cloud.loadbalancer")
public class LbConfigProps {

    private List<ServiceConfig> instances;

    @Getter
    @Setter
    public static class ServiceConfig {
        private String name;
        private String servers;
    }

}