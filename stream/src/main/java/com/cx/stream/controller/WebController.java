package com.cx.stream.controller;

import com.cx.stream.dto.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class WebController {

    @Autowired
    private StreamBridge streamBridge;

    @GetMapping(value = "item")
    public ResponseEntity<Void> delegateToSupplier() {
        final Item item = new Item("id-%d".formatted(new Random().nextInt()), "Generated an item");
        System.out.println("Sending " + item.toString());
        streamBridge.send(" generateItems-out-0", item);
        return ResponseEntity.ofNullable(null);
    }
}
