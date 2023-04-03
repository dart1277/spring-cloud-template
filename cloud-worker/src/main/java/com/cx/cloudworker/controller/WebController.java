package com.cx.cloudworker.controller;

import com.cx.cloudworker.client.ItemFeignClient;
import com.cx.cloudworker.dto.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@Validated
@RequestMapping(value = "web")
@RequiredArgsConstructor
public class WebController {

    private final WebClient.Builder loadBalancedWebClientBuilder;
    private final ItemFeignClient feignClient;

    private final ReactiveCircuitBreakerFactory circuitBreakerFactory;

    @GetMapping(value = "item/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> getItems(@PathVariable String id) {
        final Item item = loadBalancedWebClientBuilder.build().get()
                //.uri("http://localhost:9090/web/item/" + id)
                .uri("http://basic-cloud-other-worker/web/item/" + id)
                .retrieve()
                .bodyToMono(Item.class)
                .transform(it -> {
                    ReactiveCircuitBreaker rcb = circuitBreakerFactory.create("item-service"); //.create("default");
                    return rcb.run(it, throwable -> Mono.just(Item.builder().build()));
                })
                .block();

        return ResponseEntity.ok(item);

    }

    @GetMapping(value = "sync-item/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> getItemsSync(@PathVariable String id) {
        final Item item = feignClient.getItemById(id);
        return ResponseEntity.ok(item);

    }

}
