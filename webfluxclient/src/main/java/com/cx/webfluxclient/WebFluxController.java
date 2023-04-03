package com.cx.webfluxclient;

import io.micrometer.context.ContextSnapshot;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.contextpropagation.ObservationThreadLocalAccessor;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
public class WebFluxController {

    private final ObservationRegistry registry;
    private static final Logger log = LoggerFactory.getLogger(WebFluxController.class);
    private final Tracer tracer;

    private final WebClient.Builder loadBalancedWebClientBuilder;


/*
    @RequestMapping("/")
    public Mono<String> span() {
        return Mono.deferContextual(contextView -> {
            try (ContextSnapshot.Scope scope = ContextSnapshot.setThreadLocalsFrom(contextView,
                    ObservationThreadLocalAccessor.KEY)) {
                String traceId = this.tracer.currentSpan().context().traceId();
                log.info("<ACCEPTANCE_TEST> <TRACE:{}> Hello from producer", traceId);
                return Mono.just(traceId);
            }
        });
    }
*/

    @RequestMapping("/")
    public Mono<String> span() {
        return loadBalancedWebClientBuilder.build().get()
                //.uri("http://localhost:9090/web/item/" + id)
                .uri("http://basic-cloud-other-worker/web/")
                .header("cx", "1244")
                .retrieve()
                .bodyToMono(String.class);
    }



}
