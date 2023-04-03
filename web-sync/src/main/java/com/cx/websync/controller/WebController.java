package com.cx.websync.controller;

import com.cx.websync.dto.Item;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Validated
@RequestMapping(value = "web")
@RequiredArgsConstructor
public class WebController {

    private final ObservationRegistry registry;

    @GetMapping(value = "item/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> getItems(@PathVariable String id) {
        return ResponseEntity.ok(Item.builder().id("2").info("Sample info sync").build());

    }

}
