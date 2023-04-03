package com.cx.stream.processor;

import com.cx.stream.dto.Item;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.function.Supplier;

@Configuration
public class SupplierConfig {

    @Bean
    public  Supplier<Message<Item>> generateItems() {

        return () ->
                MessageBuilder.withPayload(new Item("id-%d".formatted(new Random().nextInt()), "Generated an item")).setHeader("q", "slow").build();
    }

}
