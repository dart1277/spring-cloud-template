package com.cx.cloudworker.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomHealthCheck implements HealthIndicator {

    int errorCount = 0;

    @Override
    public Health getHealth(final boolean includeDetails) {
        return HealthIndicator.super.getHealth(includeDetails);
    }

    @Override
    public Health health() {
        errorCount++;
        if(errorCount > 2 && errorCount < 4) {
            log.error("Instance down");
            return Health.down().withDetail("Custom error code", errorCount).build();
        }
        log.warn("Instance up");
        return Health.up().build();
    }
}
