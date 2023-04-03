package com.cx.cloudotherworker;

import feign.Capability;
import feign.Capability;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Hooks;


@SpringBootApplication
public class CloudOtherWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudOtherWorkerApplication.class, args);
	}

	@PostConstruct
	public void init() {
		Hooks.enableAutomaticContextPropagation();
	}

}
