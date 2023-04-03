package com.cx.loadbalanced;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class LoadbalancedApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoadbalancedApplication.class, args);
	}

}
