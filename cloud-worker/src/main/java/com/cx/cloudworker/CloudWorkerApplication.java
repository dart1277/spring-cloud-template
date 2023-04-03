package com.cx.cloudworker;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import reactor.core.publisher.Hooks;

@EnableFeignClients
@SpringBootApplication
public class CloudWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudWorkerApplication.class, args);
	}


	@PostConstruct
	public void init() {
		Hooks.enableAutomaticContextPropagation();
	}

}
