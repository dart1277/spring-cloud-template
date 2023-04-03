package com.cx.webfluxclient;

import brave.Tracing;
import brave.propagation.B3Propagation;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.reactive.WebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Hooks;

@SpringBootApplication
public class WebfluxclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxclientApplication.class, args);
	}


	@Bean
	@LoadBalanced
	public WebClient.Builder loadBalancedWebClient(final ObjectProvider<WebClientCustomizer> customizerProvider) {
		final WebClient.Builder builder = WebClient.builder();
		customizerProvider.orderedStream().forEach((customizer) -> customizer.customize(builder));
		return builder;
	}

	@Bean
	public Tracing braveTracing() {
		return Tracing.newBuilder()
				.propagationFactory(B3Propagation.newFactoryBuilder().injectFormat(B3Propagation.Format.SINGLE_NO_PARENT).build())
				.build();
	}

	@PostConstruct
	public void init() {
		Hooks.enableAutomaticContextPropagation();
	}


}
