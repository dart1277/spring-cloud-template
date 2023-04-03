package com.cx.cloudotherworker.controller;

import com.cx.cloudotherworker.dto.Item;
import io.micrometer.context.ContextSnapshot;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.contextpropagation.ObservationThreadLocalAccessor;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.observability.micrometer.Micrometer;
import reactor.core.publisher.Mono;

@RestController
@Validated
@RequestMapping(value = "web")
@RequiredArgsConstructor
public class WebController {

    private final ObservationRegistry registry;
    private static final Logger log = LoggerFactory.getLogger(WebController.class);
    private final Tracer tracer;

    @GetMapping(value = "item/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Item> getItems(@PathVariable String id) {
        return Mono.just(Item.builder().id("1").info("Sample info").build())
                .tap(Micrometer.observation(registry));

    }

    @RequestMapping("/")
    public Mono<String> span(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        log.warn(headers.toSingleValueMap().toString());
        return Mono.deferContextual(contextView -> {
            try (ContextSnapshot.Scope scope = ContextSnapshot.setThreadLocalsFrom(contextView,
                    ObservationThreadLocalAccessor.KEY)) {
                String traceId = this.tracer.currentSpan().context().traceId();
                log.info("<ACCEPTANCE_TEST> <TRACE:{}> Hello from producer", traceId);
                return Mono.just(traceId);
            }
        });
    }

}
