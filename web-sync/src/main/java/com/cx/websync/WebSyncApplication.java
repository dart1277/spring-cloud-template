package com.cx.websync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class WebSyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebSyncApplication.class, args);
	}

}
