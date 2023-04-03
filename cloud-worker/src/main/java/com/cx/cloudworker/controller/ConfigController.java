package com.cx.cloudworker.controller;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@Validated
@RequestMapping(value = "config")
public class ConfigController {



    @GetMapping("values")
    public ResponseEntity<ConfigDto> getConfig() {
        return ResponseEntity.ok(ConfigDto.builder().value(val).secret(sec).build());
    }

    @Value("${app.cfg.value}") private String val;
    @Value("${app.cfg.secret}") private String sec;
    @EventListener(ApplicationReadyEvent.class)
    public void  appReady() {
        System.out.println(String.format("Config value %s %s", val, sec));
    }
}

@Data
@Jacksonized
@Builder
class ConfigDto {
    private String value;
    private String secret;
}