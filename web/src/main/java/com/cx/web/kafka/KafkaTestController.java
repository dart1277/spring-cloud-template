package com.cx.web.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@Validated
public class KafkaTestController {
    private final KafkaProd kafkaProducer;

    @PostMapping(path = "kafka/send", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> postMessage(@RequestBody String payload) {
        log.warn(payload);
        kafkaProducer.publish(payload);
        return ResponseEntity.ok().body(payload);
    }
}
