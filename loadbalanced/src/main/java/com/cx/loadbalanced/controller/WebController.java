package com.cx.loadbalanced.controller;

import com.cx.loadbalanced.client.ItemFeignClient;
import com.cx.loadbalanced.dto.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping(value = "web")
@RequiredArgsConstructor
public class WebController {

    private final ItemFeignClient feignClient;


    @GetMapping(value = "sync-item/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> getItemsSync(@PathVariable String id) {
        final Item item = feignClient.getItemById(id);
        return ResponseEntity.ok(item);

    }

}

